package cn.org.subit.website

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
enum class CmsRole { NONE, EDITOR, ADMIN }

@Serializable
data class ApiError(val error: String)

@Serializable
data class MeResponse(val userId: Int, val username: String, val email: String?, val role: CmsRole, val csrfToken: String)

@Serializable
data class CmsUser(val userId: Int, val username: String?, val email: String?, val role: CmsRole)

@Serializable
data class RoleUpdateRequest(val role: CmsRole)

@Serializable
data class DraftResponse(val content: JsonElement, val updatedAt: String?, val updatedBy: Int?)

@Serializable
data class DraftUpdateRequest(val content: JsonElement)

@Serializable
data class PublishRequest(val content: JsonElement? = null, val publishAt: String? = null)

@Serializable
data class ReleaseInfo(val id: String, val status: String, val scheduledAt: String?, val publishedAt: String?, val publishedBy: Int)

@Serializable
data class PublishResponse(val release: ReleaseInfo)

@Serializable
data class UploadResponse(val url: String, val sha256: String, val mediaType: String, val bytes: Long)

@Serializable
data class CurrentManifest(val version: Int = 1, val releaseId: String, val contentUrl: String, val publishedAt: String)

@Serializable
data class SsoTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long,
)

@Serializable
data class SsoBasicUser(val id: Int, val username: String? = null, val email: List<String> = emptyList())

@Serializable
data class SsoInfoResponse(val user: SsoBasicUser)

data class SessionPrincipal(
    val sessionHash: String,
    val userId: Int,
    val username: String,
    val email: String?,
    val role: CmsRole,
    val csrfToken: String,
)
