package cn.org.subit.website

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.http.*
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.partialcontent.PartialContent
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.toByteArray
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.UUID
import kotlin.io.path.exists
import kotlin.io.path.readText

private const val SESSION_COOKIE = "subit_cms_session"
private const val STATE_COOKIE = "subit_cms_oauth_state"

fun main() {
    val config = AppConfig.fromEnvironment()
    embeddedServer(Netty, host = config.host, port = config.port) { module(config) }.start(wait = true)
}

fun Application.module(config: AppConfig = AppConfig.fromEnvironment()) {
    val jsonCodec = Json { ignoreUnknownKeys = true; explicitNulls = false; prettyPrint = true }
    val database = Database(config, jsonCodec)
    val initialContent = config.bootstrapContentFile?.takeIf { it.exists() }?.readText()?.let(jsonCodec::parseToJsonElement)
    database.bootstrap(config.bootstrapAdminIds, initialContent)
    val publisher = Publisher(config, database, jsonCodec)
    val httpClient = HttpClient(CIO) { install(ClientContentNegotiation) { json(jsonCodec) } }
    val ssoClient = SsoClient(config, httpClient)
    val appLog = log

    monitor.subscribe(ApplicationStopped) { httpClient.close(); database.close() }
    launch {
        while (isActive) {
            runCatching { publisher.publishDue() }.onFailure { appLog.error("Scheduled publish failed", it) }
            delay(30_000)
        }
    }

    install(CallLogging)
    install(AutoHeadResponse)
    install(PartialContent)
    install(ContentNegotiation) { json(jsonCodec) }
    install(StatusPages) {
        exception<RequestFinished> { _, _ -> }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ApiError(cause.message ?: "Invalid request"))
        }
        exception<Throwable> { call, cause ->
            appLog.error("Unhandled request error", cause)
            call.respond(HttpStatusCode.InternalServerError, ApiError("服务暂时不可用"))
        }
    }
    if (config.allowedOrigins.isNotEmpty()) install(CORS) {
        allowCredentials = true
        allowHeader(HttpHeaders.ContentType)
        allowHeader("X-CSRF-Token")
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
        config.allowedOrigins.forEach { origin -> URI(origin).let { allowHost(it.authority, schemes = listOf(it.scheme)) } }
    }

    routing {
        get("/health") { call.respond(mapOf("status" to "ok")) }
        staticFiles("/content", config.publicDir.toFile())
        route("/api") {
            get("/auth/login") {
                val state = Crypto.token()
                val expiry = Instant.now().plus(Duration.ofMinutes(10)).epochSecond
                val payload = "$state.$expiry"
                call.response.cookies.append(Cookie(
                    STATE_COOKIE,
                    "$payload.${Crypto.sign(payload, config.sessionSecret)}",
                    httpOnly = true,
                    secure = config.cookieSecure,
                    path = "/",
                    maxAge = 600,
                    extensions = mapOf("SameSite" to "Lax"),
                ))
                val callback = "${config.publicOrigin}/content-api/api/auth/callback?state=${encode(state)}"
                val destination = "${config.ssoFrontendUrl}/oauth?needAuthorize=${config.ssoServiceId}&from=${encode(callback)}"
                call.respondRedirect(destination)
            }
            get("/auth/callback") {
                val state = call.request.queryParameters["state"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, ApiError("缺少 state"))
                val code = call.request.queryParameters["code"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, ApiError("缺少授权码"))
                if (!verifyState(call.request.cookies[STATE_COOKIE], state, config.sessionSecret))
                    return@get call.respond(HttpStatusCode.BadRequest, ApiError("登录状态已过期，请重新登录"))
                val ssoUser = ssoClient.identify(code)
                val user = database.upsertSsoUser(
                    ssoUser.id, ssoUser.username, ssoUser.email.firstOrNull(), config.bootstrapAdminIds,
                )
                val sessionToken = Crypto.token(48)
                val csrf = Crypto.token()
                database.createSession(user.userId, sessionToken, csrf, Instant.now().plus(Duration.ofDays(30)))
                call.response.cookies.append(Cookie(
                    SESSION_COOKIE,
                    sessionToken,
                    httpOnly = true,
                    secure = config.cookieSecure,
                    path = "/",
                    maxAge = 2_592_000,
                    extensions = mapOf("SameSite" to "Lax"),
                ))
                call.response.cookies.append(expiredCookie(STATE_COOKIE, config))
                call.respondRedirect("${config.publicOrigin}/content-editor")
            }
            get("/auth/me") {
                val principal = call.principal(database)
                    ?: return@get call.respond(HttpStatusCode.Unauthorized, ApiError("未登录"))
                call.respond(MeResponse(
                    principal.userId, principal.username, principal.email, principal.role, principal.csrfToken,
                ))
            }
            post("/auth/logout") {
                call.requireRole(database, CmsRole.NONE, csrf = true)
                val token = call.request.cookies[SESSION_COOKIE]!!
                database.deleteSession(token)
                call.response.cookies.append(expiredCookie(SESSION_COOKIE, config))
                call.respond(HttpStatusCode.NoContent)
            }
            route("/content") {
                get("/draft") {
                    call.requireRole(database, CmsRole.EDITOR)
                    database.getDraft()?.let { call.respond(it) }
                        ?: call.respond(HttpStatusCode.NotFound, ApiError("尚无草稿"))
                }
                put("/draft") {
                    val principal = call.requireRole(database, CmsRole.EDITOR, csrf = true)
                    val request = call.receive<DraftUpdateRequest>()
                    validateContent(request.content)
                    call.respond(database.saveDraft(request.content, principal.userId))
                }
                post("/publish") {
                    val principal = call.requireRole(database, CmsRole.EDITOR, csrf = true)
                    val request = call.receive<PublishRequest>()
                    val content = request.content ?: database.getDraft()?.content
                        ?: throw IllegalArgumentException("尚无可发布的草稿")
                    validateContent(content)
                    val scheduledAt = request.publishAt?.let {
                        runCatching { Instant.parse(it) }.getOrElse { throw IllegalArgumentException("无效的发布时间") }
                    }
                    val release = database.createRelease(content, principal.userId, scheduledAt)
                    val publishNow = scheduledAt == null || !scheduledAt.isAfter(Instant.now())
                    call.respond(PublishResponse(if (publishNow) publisher.publish(release, content) else release))
                }
                get("/releases") {
                    call.requireRole(database, CmsRole.EDITOR)
                    call.respond(database.releases())
                }
                post("/releases/{id}/rollback") {
                    val principal = call.requireRole(database, CmsRole.EDITOR, csrf = true)
                    val sourceId = releaseId(call.parameters["id"])
                    val content = database.getReleaseContent(sourceId)
                        ?: return@post call.respond(HttpStatusCode.NotFound, ApiError("版本不存在"))
                    val release = database.createRelease(content, principal.userId, null)
                    call.respond(PublishResponse(publisher.publish(release, content)))
                }
                delete("/releases/{id}") {
                    val principal = call.requireRole(database, CmsRole.EDITOR, csrf = true)
                    val id = releaseId(call.parameters["id"])
                    if (database.cancelRelease(id, principal.userId)) call.respond(HttpStatusCode.NoContent)
                    else call.respond(HttpStatusCode.Conflict, ApiError("只能取消尚未发布的计划"))
                }
                post("/assets") {
                    call.requireRole(database, CmsRole.EDITOR, csrf = true)
                    val multipart = call.receiveMultipart(formFieldLimit = config.maxUploadBytes)
                    var response: UploadResponse? = null
                    multipart.forEachPart { part ->
                        if (part is PartData.FileItem && response == null) {
                            val mediaType = part.contentType?.toString() ?: "application/octet-stream"
                            val extension = extensionFor(mediaType)
                                ?: throw IllegalArgumentException("仅支持 PNG、JPEG、WebP 和 GIF")
                            val bytes = part.provider().toByteArray()
                            require(bytes.size <= config.maxUploadBytes) { "图片超过上传限制" }
                            val (url, digest) = publisher.storeAsset(bytes, extension)
                            response = UploadResponse(url, digest, mediaType, bytes.size.toLong())
                        }
                        part.dispose()
                    }
                    response?.let { call.respond(it) }
                        ?: call.respond(HttpStatusCode.BadRequest, ApiError("没有收到图片"))
                }
            }
            route("/admin") {
                get("/users") {
                    call.requireRole(database, CmsRole.ADMIN)
                    call.respond(database.getUsers())
                }
                put("/users/{id}/role") {
                    val principal = call.requireRole(database, CmsRole.ADMIN, csrf = true)
                    val userId = call.parameters["id"]?.toIntOrNull()
                        ?: throw IllegalArgumentException("无效用户 ID")
                    val request = call.receive<RoleUpdateRequest>()
                    require(request.role != CmsRole.NONE || userId != principal.userId) {
                        "不能移除自己的管理员权限"
                    }
                    call.respond(database.setRole(userId, request.role, principal.userId))
                }
            }
        }
    }
}

private fun ApplicationCall.principal(database: Database): SessionPrincipal? =
    request.cookies[SESSION_COOKIE]?.let(database::getSession)

private suspend fun ApplicationCall.requireRole(
    database: Database,
    minimum: CmsRole,
    csrf: Boolean = false,
): SessionPrincipal {
    val principal = principal(database) ?: run {
        respond(HttpStatusCode.Unauthorized, ApiError("未登录")); throw RequestFinished()
    }
    if (principal.role.ordinal < minimum.ordinal) {
        respond(HttpStatusCode.Forbidden, ApiError("没有权限")); throw RequestFinished()
    }
    if (csrf && !Crypto.constantEquals(request.headers["X-CSRF-Token"].orEmpty(), principal.csrfToken)) {
        respond(HttpStatusCode.Forbidden, ApiError("CSRF 校验失败")); throw RequestFinished()
    }
    return principal
}

private class RequestFinished : RuntimeException()

private fun verifyState(cookie: String?, expected: String, secret: String): Boolean {
    val parts = cookie?.split('.') ?: return false
    if (parts.size != 3 || parts[0] != expected) return false
    val expiry = parts[1].toLongOrNull() ?: return false
    val payload = "${parts[0]}.${parts[1]}"
    return expiry >= Instant.now().epochSecond &&
        Crypto.constantEquals(parts[2], Crypto.sign(payload, secret))
}

internal fun validateContent(content: JsonElement) {
    val root = content as? JsonObject ?: throw IllegalArgumentException("内容必须是 JSON 对象")
    require(root["version"]?.jsonPrimitive?.intOrNull == 1) { "不支持的内容版本" }
    require(root["projects"] is JsonArray && root["achievements"] is JsonArray) {
        "缺少 projects 或 achievements"
    }
    require(root["join"] is JsonObject && root["submore"] is JsonObject) { "缺少 join 或 submore" }
}

private fun extensionFor(mediaType: String) = when (mediaType.substringBefore(';').lowercase()) {
    "image/png" -> "png"
    "image/jpeg" -> "jpg"
    "image/webp" -> "webp"
    "image/gif" -> "gif"
    else -> null
}

private fun encode(value: String) = URLEncoder.encode(value, StandardCharsets.UTF_8)

private fun releaseId(value: String?): String = value
    ?.takeIf { runCatching { UUID.fromString(it) }.isSuccess }
    ?: throw IllegalArgumentException("无效版本 ID")

private fun expiredCookie(name: String, config: AppConfig) = Cookie(
    name,
    "",
    httpOnly = true,
    secure = config.cookieSecure,
    path = "/",
    maxAge = 0,
    extensions = mapOf("SameSite" to "Lax"),
)
