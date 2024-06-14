package com.celvine.deb.esail.bby.data.viewmodels

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.common.RegisterState
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.model.RegistrationResponse
import com.celvine.deb.esail.bby.data.model.SendOTP
import com.celvine.deb.esail.bby.data.model.VerifyOTP
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel() : ViewModel() {
    private val apiService = ApiConfig.instanceRetrofit

    private val _uiState = MutableLiveData(RegisterState())
    val uiState: LiveData<RegisterState> = _uiState

    fun updateName(name: TextFieldValue) {
        _uiState.value = _uiState.value?.copy(
            name = name,
            nameError = if (isValidName(name.text)) null else "Invalid name format")
    }

    fun updateEmail(email: TextFieldValue) {
        _uiState.value = _uiState.value?.copy(
            email = email,
            emailError = if (isValidEmail(email.text)) null else "Invalid email format")
    }

    fun updatePassword(password: TextFieldValue) {
        _uiState.value = _uiState.value?.copy(
            password = password,
            passwordError = if (isValidPassword(password.text)) null else "Password must be at least 8 character")
    }

    fun updateCode(code: TextFieldValue) {
        _uiState.value = _uiState.value?.copy(
            code = code,
            codeError = if (isValidCode(code.text)) null else "Invalid code")
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value?.copy(isLoading = isLoading)
    }

    fun isValidName(name: String): Boolean {
        return name.length >= 2
    }

    fun isValidCode(name: String): Boolean {
        return name.length >= 2
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun registerUser(name: String, email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        apiService.register(name, email, password).enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    if (responModel != null) {
//                        sendActivationOTP(email, onSuccess, onError)
                        onSuccess.invoke()
                    } else {
                        onError.invoke("Failed to send OTP")
                    }
                } else {
                    onError.invoke("Registration error")
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                onError.invoke("Registration failed: ${t.message}")
            }
        })
    }

    fun sendActivationOTP(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        apiService.sendActivationOTP(email).enqueue(object : Callback<SendOTP> {
            override fun onResponse(call: Call<SendOTP>, response: Response<SendOTP>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    if (responModel != null) {
                        onSuccess.invoke()
                    } else {
                        onError.invoke("Failed to send OTP")
                    }
                } else {
                    onError.invoke("Failed to send OTP")
                }
            }

            override fun onFailure(call: Call<SendOTP>, t: Throwable) {
                onError.invoke("Failed to send OTP: ${t.message}")
            }
        })
    }

    fun verifyActivationOTP(data: AccountVerificationOTP, onSuccess: () -> Unit, onError: (String) -> Unit) {
        apiService.verifyActivationOTP(data).enqueue(object : Callback<VerifyOTP> {
            override fun onResponse(call: Call<VerifyOTP>, response: Response<VerifyOTP>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    if (responModel != null) {
                        onSuccess.invoke()
                    } else {
                        onError.invoke("OTP verification failed")
                    }
                } else {
                    onError.invoke("OTP verification failed")
                }
            }

            override fun onFailure(call: Call<VerifyOTP>, t: Throwable) {
                onError.invoke("OTP verification failed: ${t.message}")
            }
        })
    }
}


