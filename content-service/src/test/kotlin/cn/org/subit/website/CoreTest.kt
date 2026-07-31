package cn.org.subit.website

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class CoreTest {
    private val validContent = Json.parseToJsonElement("""
        {"version":1,"projects":[],"achievements":[],"join":{"members":[]},"submore":{"pots":[]}}
    """.trimIndent())

    @Test
    fun validatesSupportedContentShape() {
        validateContent(validContent)
    }

    @Test
    fun rejectsUnsupportedVersion() {
        val invalid = Json.parseToJsonElement("""
            {"version":2,"projects":[],"achievements":[],"join":{},"submore":{}}
        """.trimIndent())
        assertFailsWith<IllegalArgumentException> { validateContent(invalid) }
    }

    @Test
    fun signaturesAreStableAndSecretBound() {
        val first = Crypto.sign("state.123", "secret-a")
        assertEquals(first, Crypto.sign("state.123", "secret-a"))
        assertNotEquals(first, Crypto.sign("state.123", "secret-b"))
        assertTrue(Crypto.constantEquals(first, first))
    }

    @Test
    fun environmentConfigurationParsesRolesAndLimits() {
        val config = AppConfig.fromEnvironment(mapOf(
            "DATABASE_URL" to "jdbc:postgresql://localhost/test",
            "DATABASE_USER" to "cms",
            "DATABASE_PASSWORD" to "password",
            "SSO_SERVICE_ID" to "42",
            "SSO_SERVICE_TOKEN" to "service-token",
            "SESSION_SECRET" to "a-32-byte-minimum-session-secret!!",
            "BOOTSTRAP_ADMIN_IDS" to "1, 3,invalid",
            "MAX_UPLOAD_BYTES" to "1024",
        ))
        assertEquals(setOf(1, 3), config.bootstrapAdminIds)
        assertEquals(1024, config.maxUploadBytes)
        assertEquals(42, config.ssoServiceId)
        assertEquals("127.0.0.1", config.host)
    }

    @Test
    fun rejectsShortSessionSecret() {
        assertFailsWith<IllegalArgumentException> {
            AppConfig.fromEnvironment(mapOf(
                "DATABASE_URL" to "jdbc:postgresql://localhost/test",
                "DATABASE_USER" to "cms",
                "DATABASE_PASSWORD" to "password",
                "SSO_SERVICE_ID" to "42",
                "SSO_SERVICE_TOKEN" to "service-token",
                "SESSION_SECRET" to "too-short",
            ))
        }
    }
}
