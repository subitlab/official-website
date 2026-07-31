package cn.org.subit.website

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Instant
import kotlin.io.path.createDirectories

class Publisher(private val config: AppConfig, private val database: Database, private val json: Json) {
    init {
        config.publicDir.resolve("releases").createDirectories()
        config.publicDir.resolve("assets").createDirectories()
    }

    fun publish(release: ReleaseInfo, content: JsonElement): ReleaseInfo {
        val publishedAt = release.publishedAt ?: Instant.now().toString()
        val releasePath = config.publicDir.resolve("releases/${release.id}.json")
        if (!Files.exists(releasePath)) atomicWrite(releasePath, json.encodeToString(JsonElement.serializer(), content))
        atomicWrite(config.publicDir.resolve("site-content.json"), json.encodeToString(JsonElement.serializer(), content))
        atomicWrite(config.publicDir.resolve("current.json"), json.encodeToString(CurrentManifest(
            releaseId = release.id,
            contentUrl = "/content/releases/${release.id}.json",
            publishedAt = publishedAt,
        )))
        database.markPublished(release.id)
        return release.copy(status = "PUBLISHED", publishedAt = publishedAt)
    }

    fun publishDue(): Int {
        val due = database.dueReleases()
        due.forEach { (release, content) -> publish(release, content) }
        return due.size
    }

    fun storeAsset(bytes: ByteArray, extension: String): Pair<String, String> {
        val digest = Crypto.sha256(bytes)
        val fileName = "$digest.$extension"
        val destination = config.publicDir.resolve("assets/$fileName")
        if (!Files.exists(destination)) atomicWrite(destination, bytes)
        return "/content/assets/$fileName" to digest
    }

    private fun atomicWrite(path: java.nio.file.Path, value: String) = atomicWrite(path, value.toByteArray())

    private fun atomicWrite(path: java.nio.file.Path, value: ByteArray) {
        path.parent.createDirectories()
        val temporary = Files.createTempFile(path.parent, ".${path.fileName}.", ".tmp")
        Files.write(temporary, value)
        try {
            Files.move(temporary, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING)
        } catch (_: java.nio.file.AtomicMoveNotSupportedException) {
            Files.move(temporary, path, StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
