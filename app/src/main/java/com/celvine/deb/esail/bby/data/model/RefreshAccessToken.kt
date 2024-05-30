package com.celvine.deb.esail.bby.data.model

data class RefreshAccessToken(
    val statusCode: String,
    val message: String,
    val data: RefreshTokenData
)