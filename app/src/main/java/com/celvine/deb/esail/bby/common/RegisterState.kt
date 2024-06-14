package com.celvine.deb.esail.bby.common

import androidx.compose.ui.text.input.TextFieldValue

data class RegisterState(
    val name: TextFieldValue = TextFieldValue(),
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val code: TextFieldValue = TextFieldValue(),
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val codeError: String? = null,
    val isLoading: Boolean = false
)