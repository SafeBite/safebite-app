package com.celvine.deb.esail.bby.data.model

import android.service.autofill.UserData

data class ResponModel(
    var statusCode: String = "",
    var data: ActivationData,
    var success: Int = 0,
    var message: String,
//    var user: UserData,
    var sendOtp: sendOtp = sendOtp(),
    var access: AccessToken,
    var refresh: RefreshToken,
    var token: String
)
