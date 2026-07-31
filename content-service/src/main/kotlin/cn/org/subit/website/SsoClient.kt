package cn.org.subit.website

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess

class SsoClient(private val config: AppConfig, private val client: HttpClient) {
    suspend fun identify(code: String): SsoBasicUser {
        val tokenResponse = client.get("${config.ssoApiUrl}/serviceApi/oauth/accessToken?time=3600") {
            header(HttpHeaders.Authorization, "Bearer ${config.ssoServiceToken}")
            header("Oauth-Code", "Bearer $code")
        }
        check(tokenResponse.status.isSuccess()) {
            "SSubitO rejected the authorization code (${tokenResponse.status.value})"
        }
        val tokens = tokenResponse.body<SsoTokenResponse>()
        val infoResponse = client.get("${config.ssoApiUrl}/serviceApi/info") {
            header(HttpHeaders.Authorization, "Bearer ${tokens.accessToken}")
        }
        check(infoResponse.status.isSuccess()) {
            "SSubitO user lookup failed (${infoResponse.status.value})"
        }
        return infoResponse.body<SsoInfoResponse>().user
    }
}
