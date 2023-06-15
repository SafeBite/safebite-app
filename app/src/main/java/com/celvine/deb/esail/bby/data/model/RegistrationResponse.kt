package com.celvine.deb.esail.bby.data.model

data class RegistrationResponse(
    val statusCode: String,
    val message: String,
    val data: RegistrationData
)

data class RegistrationData(
    val uuid: String
)