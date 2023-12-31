package com.biryulindevelop.data.api.authentication

import com.biryulindevelop.common.ACCESS_KEY
import com.biryulindevelop.common.REDIRECT_URI
import com.biryulindevelop.common.SECRET_KEY
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiToken {

    @POST("https://unsplash.com/oauth/token")
    suspend fun getToken(
        @Query("code") code: String,
        @Query("client_id") clientId: String = ACCESS_KEY,
        @Query("client_secret") clientSecret: String = SECRET_KEY,
        @Query("redirect_uri") redirectUri: String = REDIRECT_URI,
        @Query("grant_type") grantType: String = "authorization_code"
    ): TokenResponse

    data class TokenResponse(
        val access_token: String
    )
}
