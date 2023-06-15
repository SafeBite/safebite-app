package com.celvine.deb.esail.bby.data.model

data class ActivationData(
    val uuid: String,
    val access: Token,
    val refresh: Token
)

data class Token(
    val token: String,
    val expiredAt: Long
)