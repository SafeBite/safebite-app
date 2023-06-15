package com.celvine.deb.esail.bby.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)

//data class LoginResponse (
//    val AccessToken: access,
//    val refresh: RefreshToken
//)