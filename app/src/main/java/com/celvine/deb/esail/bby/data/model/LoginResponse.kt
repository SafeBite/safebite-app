package com.celvine.deb.esail.bby.data.model

data class LoginResponse(
    val statusCode: String,
    val message: String,
    val data: LoginData
)
