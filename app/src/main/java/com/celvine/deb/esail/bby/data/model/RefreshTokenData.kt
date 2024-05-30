package com.celvine.deb.esail.bby.data.model

data class RefreshTokenData(
    val uuid: String,
    val access: TokenAccess,
    val refresh: TokenRefresh
)
