package cn.org.subit.website

import java.nio.file.Path
import kotlin.io.path.Path

data class AppConfig(
    val host: String,
    val port: Int,
    val databaseUrl: String,
    val databaseUser: String,
    val databasePassword: String,
    val publicDir: Path,
    val bootstrapContentFile: Path?,
    val publicOrigin: String,
    val ssoFrontendUrl: String,
    val ssoApiUrl: String,
    val ssoServiceId: Int,
    val ssoServiceToken: String,
    val sessionSecret: String,
    val cookieSecure: Boolean,
    val bootstrapAdminIds: Set<Int>,
    val allowedOrigins: Set<String>,
    val maxUploadBytes: Long,
) {
    companion object {
        fun fromEnvironment(env: Map<String, String> = System.getenv()): AppConfig {
            fun required(name: String) = env[name]?.takeIf(String::isNotBlank)
                ?: error("Missing required environment variable: $name")
            fun csv(name: String) = env[name].orEmpty().split(',').map(String::trim).filter(String::isNotEmpty).toSet()
            return AppConfig(
                host = env["BIND_HOST"]?.takeIf(String::isNotBlank) ?: "127.0.0.1",
                port = env["PORT"]?.toIntOrNull() ?: 8091,
                databaseUrl = required("DATABASE_URL"),
                databaseUser = required("DATABASE_USER"),
                databasePassword = required("DATABASE_PASSWORD"),
                publicDir = Path(env["CONTENT_PUBLIC_DIR"] ?: "./data/public"),
                bootstrapContentFile = env["BOOTSTRAP_CONTENT_FILE"]?.let(::Path),
                publicOrigin = (env["PUBLIC_ORIGIN"] ?: "http://localhost:5173").trimEnd('/'),
                ssoFrontendUrl = (env["SSO_FRONTEND_URL"] ?: "https://ssubito.subit.org.cn").trimEnd('/'),
                ssoApiUrl = (env["SSO_API_URL"] ?: "https://sso.subit.org.cn/api").trimEnd('/'),
                ssoServiceId = required("SSO_SERVICE_ID").toInt().also { require(it > 0) { "SSO_SERVICE_ID must be positive" } },
                ssoServiceToken = required("SSO_SERVICE_TOKEN"),
                sessionSecret = required("SESSION_SECRET").also {
                    require(it.toByteArray().size >= 32) { "SESSION_SECRET must contain at least 32 bytes" }
                },
                cookieSecure = env["COOKIE_SECURE"]?.toBooleanStrictOrNull() ?: true,
                bootstrapAdminIds = csv("BOOTSTRAP_ADMIN_IDS").mapNotNull(String::toIntOrNull).toSet(),
                allowedOrigins = csv("ALLOWED_ORIGINS"),
                maxUploadBytes = (env["MAX_UPLOAD_BYTES"]?.toLongOrNull() ?: 8L * 1024 * 1024).also {
                    require(it > 0) { "MAX_UPLOAD_BYTES must be positive" }
                },
            )
        }
    }
}
