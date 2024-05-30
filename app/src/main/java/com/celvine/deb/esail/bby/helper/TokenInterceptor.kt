package com.celvine.deb.esail.bby.helper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.celvine.deb.esail.bby.api.ApiService
import com.celvine.deb.esail.bby.data.model.RefreshAccessToken
import com.celvine.deb.esail.bby.data.model.RefreshTokenRequest
import okhttp3.Interceptor
import okhttp3.Request
import java.time.Instant
import okhttp3.Response as OkHttpResponse
import retrofit2.Response as RetrofitResponse


class TokenInterceptor(
    private val sessionManager: SessionManager,
    private val apiService: ApiService
) : Interceptor {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun intercept(chain: Interceptor.Chain): OkHttpResponse {
        var request = chain.request()
        var accessToken = sessionManager.fetchAuthToken()
        Log.d("TokenInterceptor", "Original Access Token: $accessToken")

        if (accessToken.isNullOrEmpty() || isTokenExpired()) {
            Log.d("TokenInterceptor", "Access token is either null or expired. Attempting to refresh.")
            val refreshToken = sessionManager.fetchRefreshToken()
            Log.d("TokenInterceptor", "Refresh Token: $refreshToken")

            if (!refreshToken.isNullOrEmpty()) {
                val newTokenResponse: RetrofitResponse<RefreshAccessToken>
                try {
                    newTokenResponse = apiService.refreshToken(RefreshTokenRequest(refreshToken)).execute()
                    Log.d("TokenInterceptor", "Token refresh response: ${newTokenResponse.raw()}")
                } catch (e: Exception) {
                    Log.e("TokenInterceptor", "Token refresh request failed: ${e.message}")
                    return handleTokenRefreshError(chain, request)
                }

                if (newTokenResponse.isSuccessful) {
                    val refreshAccessToken = newTokenResponse.body()
                    val newAccessToken = refreshAccessToken?.data?.access?.token
                    val newAccessTokenExpiry = refreshAccessToken?.data?.access?.expiredAt
                    if (!newAccessToken.isNullOrEmpty() && newAccessTokenExpiry != null) {
                        sessionManager.saveAuthToken(newAccessToken, newAccessTokenExpiry)
                        Log.d("TokenInterceptor", "New Access Token: $newAccessToken")
                        Log.d("TokenInterceptor", "New Expiry Time: $newAccessTokenExpiry")
                        accessToken = newAccessToken
                    } else {
                        Log.e("TokenInterceptor", "Failed to get new access token from the response.")
                        return handleTokenRefreshError(chain, request)
                    }
                } else {
                    Log.e("TokenInterceptor", "Token refresh failed with response code: ${newTokenResponse.code()} and message: ${newTokenResponse.message()}")
                    return handleTokenRefreshError(chain, request)
                }
            } else {
                Log.e("TokenInterceptor", "Refresh token is null or empty.")
                return handleTokenRefreshError(chain, request)
            }
        }

        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        Log.d("TokenInterceptor", "Proceeding with request with Access Token: $accessToken")

        return chain.proceed(request)
    }

    private fun handleTokenRefreshError(chain: Interceptor.Chain, request: Request): OkHttpResponse {
        Log.e("TokenInterceptor", "Handling token refresh error.")
        return chain.proceed(request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isTokenExpired(): Boolean {
        val instant = Instant.now()
        val expiryTime = sessionManager.fetchAccessTokenExpiry()
        val isExpired = instant.epochSecond >= expiryTime
        Log.d("TokenInterceptor", "Token expiry time: $expiryTime, isExpired: $isExpired")
        return isExpired
    }
}
