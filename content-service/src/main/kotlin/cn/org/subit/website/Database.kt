package cn.org.subit.website

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

class Database(config: AppConfig, private val json: Json) : AutoCloseable {
    private val dataSource = HikariDataSource(HikariConfig().apply {
        jdbcUrl = config.databaseUrl
        username = config.databaseUser
        password = config.databasePassword
        maximumPoolSize = 6
        minimumIdle = 1
        connectionTimeout = 10_000
    })

    init { migrate() }

    private fun migrate() = tx { connection ->
        connection.createStatement().use { statement ->
            statement.execute("""
                CREATE TABLE IF NOT EXISTS website_cms_users (
                    user_id INTEGER PRIMARY KEY,
                    username TEXT,
                    email TEXT,
                    role TEXT NOT NULL DEFAULT 'NONE' CHECK (role IN ('NONE','EDITOR','ADMIN')),
                    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
                );
                CREATE TABLE IF NOT EXISTS website_cms_sessions (
                    id_hash CHAR(64) PRIMARY KEY,
                    user_id INTEGER NOT NULL REFERENCES website_cms_users(user_id) ON DELETE CASCADE,
                    csrf_token TEXT NOT NULL,
                    expires_at TIMESTAMPTZ NOT NULL,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
                );
                CREATE TABLE IF NOT EXISTS website_cms_draft (
                    singleton BOOLEAN PRIMARY KEY DEFAULT TRUE CHECK (singleton),
                    content JSONB NOT NULL,
                    updated_by INTEGER REFERENCES website_cms_users(user_id),
                    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
                );
                CREATE TABLE IF NOT EXISTS website_cms_releases (
                    id UUID PRIMARY KEY,
                    content JSONB NOT NULL,
                    status TEXT NOT NULL CHECK (status IN ('SCHEDULED','PUBLISHED','SUPERSEDED','CANCELLED')),
                    scheduled_at TIMESTAMPTZ,
                    published_at TIMESTAMPTZ,
                    published_by INTEGER NOT NULL REFERENCES website_cms_users(user_id),
                    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
                );
                CREATE INDEX IF NOT EXISTS website_cms_release_schedule ON website_cms_releases(status, scheduled_at);
                CREATE TABLE IF NOT EXISTS website_cms_audit (
                    id BIGSERIAL PRIMARY KEY,
                    user_id INTEGER REFERENCES website_cms_users(user_id),
                    action TEXT NOT NULL,
                    target TEXT,
                    detail JSONB,
                    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
                );
            """.trimIndent())
        }
    }

    fun bootstrap(adminIds: Set<Int>, initialContent: JsonElement?) = tx { connection ->
        adminIds.forEach { id ->
            connection.prepareStatement("""
                INSERT INTO website_cms_users(user_id, role) VALUES (?, 'ADMIN')
                ON CONFLICT (user_id) DO UPDATE SET role = 'ADMIN', updated_at = NOW()
            """.trimIndent()).use { statement -> statement.setInt(1, id); statement.executeUpdate() }
        }
        if (initialContent != null) {
            connection.prepareStatement("""
                INSERT INTO website_cms_draft(singleton, content) VALUES (TRUE, CAST(? AS JSONB))
                ON CONFLICT (singleton) DO NOTHING
            """.trimIndent()).use { statement ->
                statement.setString(1, json.encodeToString(JsonElement.serializer(), initialContent))
                statement.executeUpdate()
            }
        }
    }

    fun upsertSsoUser(id: Int, username: String?, email: String?, bootstrapAdmins: Set<Int>): CmsUser = tx { connection ->
        connection.prepareStatement("""
            INSERT INTO website_cms_users(user_id, username, email, role)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (user_id) DO UPDATE SET username = EXCLUDED.username, email = EXCLUDED.email, updated_at = NOW()
            RETURNING user_id, username, email, role
        """.trimIndent()).use { statement ->
            statement.setInt(1, id)
            statement.setString(2, username)
            statement.setString(3, email)
            statement.setString(4, if (id in bootstrapAdmins) CmsRole.ADMIN.name else CmsRole.NONE.name)
            statement.executeQuery().use { result -> result.next(); result.cmsUser() }
        }
    }

    fun createSession(userId: Int, rawToken: String, csrf: String, expiresAt: Instant) = tx { connection ->
        connection.prepareStatement("DELETE FROM website_cms_sessions WHERE expires_at < NOW()").use { it.executeUpdate() }
        connection.prepareStatement("INSERT INTO website_cms_sessions(id_hash,user_id,csrf_token,expires_at) VALUES (?,?,?,?)").use {
            it.setString(1, Crypto.sha256(rawToken))
            it.setInt(2, userId)
            it.setString(3, csrf)
            it.setTimestamp(4, Timestamp.from(expiresAt))
            it.executeUpdate()
        }
    }

    fun getSession(rawToken: String): SessionPrincipal? = tx { connection ->
        connection.prepareStatement("""
            SELECT s.id_hash,u.user_id,u.username,u.email,u.role,s.csrf_token
            FROM website_cms_sessions s JOIN website_cms_users u ON u.user_id=s.user_id
            WHERE s.id_hash=? AND s.expires_at>NOW()
        """.trimIndent()).use { statement ->
            statement.setString(1, Crypto.sha256(rawToken))
            statement.executeQuery().use { result ->
                if (!result.next()) null else SessionPrincipal(
                    result.getString("id_hash"),
                    result.getInt("user_id"),
                    result.getString("username") ?: "SubIT 成员",
                    result.getString("email"),
                    CmsRole.valueOf(result.getString("role")),
                    result.getString("csrf_token"),
                )
            }
        }
    }

    fun deleteSession(rawToken: String) = tx { connection ->
        connection.prepareStatement("DELETE FROM website_cms_sessions WHERE id_hash=?").use {
            it.setString(1, Crypto.sha256(rawToken)); it.executeUpdate()
        }
    }

    fun getUsers(): List<CmsUser> = tx { connection ->
        connection.prepareStatement("SELECT user_id,username,email,role FROM website_cms_users WHERE role <> 'NONE' ORDER BY role DESC,user_id").use {
            it.executeQuery().use { result -> buildList { while (result.next()) add(result.cmsUser()) } }
        }
    }

    fun setRole(userId: Int, role: CmsRole, actor: Int): CmsUser = tx { connection ->
        connection.prepareStatement("""
            INSERT INTO website_cms_users(user_id,role) VALUES (?,?)
            ON CONFLICT(user_id) DO UPDATE SET role=EXCLUDED.role,updated_at=NOW()
            RETURNING user_id,username,email,role
        """.trimIndent()).use { statement ->
            statement.setInt(1, userId)
            statement.setString(2, role.name)
            statement.executeQuery().use { result ->
                result.next()
                result.cmsUser().also { audit(connection, actor, "ROLE_CHANGED", userId.toString(), "{\"role\":\"${role.name}\"}") }
            }
        }
    }

    fun getDraft(): DraftResponse? = tx { connection ->
        connection.prepareStatement("SELECT content::text,updated_at,updated_by FROM website_cms_draft WHERE singleton=TRUE").use {
            it.executeQuery().use { result ->
                if (!result.next()) null else DraftResponse(
                    json.parseToJsonElement(result.getString(1)),
                    result.getTimestamp(2)?.toInstant()?.toString(),
                    result.getObject(3)?.let { result.getInt(3) },
                )
            }
        }
    }

    fun saveDraft(content: JsonElement, actor: Int): DraftResponse = tx { connection ->
        val raw = json.encodeToString(JsonElement.serializer(), content)
        connection.prepareStatement("""
            INSERT INTO website_cms_draft(singleton,content,updated_by,updated_at) VALUES(TRUE,CAST(? AS JSONB),?,NOW())
            ON CONFLICT(singleton) DO UPDATE SET content=EXCLUDED.content,updated_by=EXCLUDED.updated_by,updated_at=NOW()
            RETURNING content::text,updated_at,updated_by
        """.trimIndent()).use { statement ->
            statement.setString(1, raw)
            statement.setInt(2, actor)
            statement.executeQuery().use { result ->
                result.next()
                audit(connection, actor, "DRAFT_SAVED", null, null)
                DraftResponse(json.parseToJsonElement(result.getString(1)), result.getTimestamp(2).toInstant().toString(), result.getInt(3))
            }
        }
    }

    fun createRelease(content: JsonElement, actor: Int, scheduledAt: Instant?): ReleaseInfo = tx { connection ->
        val id = UUID.randomUUID()
        val effectiveSchedule = scheduledAt ?: Instant.now()
        connection.prepareStatement("""
            INSERT INTO website_cms_releases(id,content,status,scheduled_at,published_at,published_by)
            VALUES(?,CAST(? AS JSONB),'SCHEDULED',?,NULL,?)
        """.trimIndent()).use {
            it.setObject(1, id)
            it.setString(2, json.encodeToString(JsonElement.serializer(), content))
            it.setTimestamp(3, Timestamp.from(effectiveSchedule))
            it.setInt(4, actor)
            it.executeUpdate()
        }
        audit(connection, actor, "SCHEDULED", id.toString(), null)
        getRelease(connection, id.toString())!!
    }

    fun getReleaseContent(id: String): JsonElement? = tx { connection ->
        connection.prepareStatement("SELECT content::text FROM website_cms_releases WHERE id=?::uuid").use {
            it.setString(1, id)
            it.executeQuery().use { result -> if (result.next()) json.parseToJsonElement(result.getString(1)) else null }
        }
    }

    fun releases(): List<ReleaseInfo> = tx { connection ->
        connection.prepareStatement("SELECT id,status,scheduled_at,published_at,published_by FROM website_cms_releases ORDER BY created_at DESC LIMIT 100").use {
            it.executeQuery().use { result -> buildList { while (result.next()) add(result.releaseInfo()) } }
        }
    }

    fun dueReleases(): List<Pair<ReleaseInfo, JsonElement>> = tx { connection ->
        connection.prepareStatement("""
            SELECT id,status,scheduled_at,published_at,published_by,content::text AS content
            FROM website_cms_releases WHERE status='SCHEDULED' AND scheduled_at<=NOW() ORDER BY scheduled_at
        """.trimIndent()).use {
            it.executeQuery().use { result -> buildList {
                while (result.next()) add(result.releaseInfo() to json.parseToJsonElement(result.getString("content")))
            } }
        }
    }

    fun markPublished(id: String) = tx { connection ->
        connection.prepareStatement("UPDATE website_cms_releases SET status='SUPERSEDED' WHERE status='PUBLISHED' AND id<>?::uuid").use {
            it.setString(1, id); it.executeUpdate()
        }
        connection.prepareStatement("UPDATE website_cms_releases SET status='PUBLISHED',published_at=COALESCE(published_at,NOW()) WHERE id=?::uuid").use {
            it.setString(1, id); it.executeUpdate()
        }
        connection.prepareStatement("""
            INSERT INTO website_cms_audit(user_id,action,target)
            SELECT published_by,'PUBLISHED',id::text FROM website_cms_releases WHERE id=?::uuid
        """.trimIndent()).use { it.setString(1, id); it.executeUpdate() }
    }

    fun cancelRelease(id: String, actor: Int): Boolean = tx { connection ->
        val changed = connection.prepareStatement("UPDATE website_cms_releases SET status='CANCELLED' WHERE id=?::uuid AND status='SCHEDULED'").use {
            it.setString(1, id); it.executeUpdate() > 0
        }
        if (changed) audit(connection, actor, "SCHEDULE_CANCELLED", id, null)
        changed
    }

    private fun getRelease(connection: Connection, id: String): ReleaseInfo? = connection.prepareStatement(
        "SELECT id,status,scheduled_at,published_at,published_by FROM website_cms_releases WHERE id=?::uuid"
    ).use {
        it.setString(1, id)
        it.executeQuery().use { result -> if (result.next()) result.releaseInfo() else null }
    }

    private fun audit(connection: Connection, userId: Int?, action: String, target: String?, detail: String?) {
        connection.prepareStatement("INSERT INTO website_cms_audit(user_id,action,target,detail) VALUES(?,?,?,CAST(? AS JSONB))").use {
            if (userId == null) it.setNull(1, java.sql.Types.INTEGER) else it.setInt(1, userId)
            it.setString(2, action)
            it.setString(3, target)
            it.setString(4, detail)
            it.executeUpdate()
        }
    }

    private fun ResultSet.cmsUser() = CmsUser(
        getInt("user_id"), getString("username"), getString("email"), CmsRole.valueOf(getString("role")),
    )

    private fun ResultSet.releaseInfo() = ReleaseInfo(
        getString("id"),
        getString("status"),
        getTimestamp("scheduled_at")?.toInstant()?.toString(),
        getTimestamp("published_at")?.toInstant()?.toString(),
        getInt("published_by"),
    )

    private fun <T> tx(block: (Connection) -> T): T = dataSource.connection.use { connection ->
        connection.autoCommit = false
        try { block(connection).also { connection.commit() } }
        catch (error: Throwable) { connection.rollback(); throw error }
    }

    override fun close() = dataSource.close()
}
