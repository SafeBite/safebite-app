package com.celvine.deb.esail.bby.data.model

data class LoginData(
    val uuid: String,
    val access: AccessToken,
    val refresh: RefreshToken
)