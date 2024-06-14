package com.celvine.deb.esail.bby.common

import androidx.compose.ui.text.input.TextFieldValue

data class LoginState(
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false
)