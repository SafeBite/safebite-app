package com.celvine.deb.esail.bby.data.model

import User
import android.service.autofill.UserData

data class UserResponse(
    val statusCode: String,
    val message: String,
    val data: User
)
