package com.celvine.deb.esail.bby.data.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.ui.text.input.TextFieldValue
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.*
import com.celvine.deb.esail.bby.helper.SessionManager
import com.celvine.deb.esail.bby.common.LoginState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context) : ViewModel() {
    private val sessionManager: SessionManager = SessionManager(context)
    private val apiService = ApiConfig.instanceRetrofit

    private val _uiState = MutableLiveData(LoginState())
    val uiState: LiveData<LoginState> get() = _uiState

    private val _userAllergenIds = MutableLiveData<List<Int>>()
    val userAllergenIds: LiveData<List<Int>> = _userAllergenIds

    private val _userResponse = MutableLiveData<UserResponse?>()
    val userResponse: LiveData<UserResponse?> get() = _userResponse

    fun updateEmail(email: TextFieldValue) {
        _uiState.value = _uiState.value?.copy(
            email = email,
            emailError = if (isValidEmail(email.text)) null else "Invalid email"
        )
    }

    fun updatePassword(password: TextFieldValue) {
        _uiState.value = _uiState.value?.copy(
            password = password,
            passwordError = if (isValidPassword(password.text)) null else "Password must be at least 8 character"
        )
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value?.copy(isLoading = isLoading)
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun performLogin(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val email = _uiState.value?.email?.text ?: return
        val password = _uiState.value?.password?.text ?: return

//        setLoading(true)
        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    if (responModel != null) {
                        sessionManager.saveAuthToken(
                            responModel.data.access.token,
                            responModel.data.access.expiredAt
                        )
                        sessionManager.saveRefreshToken(responModel.data.refresh.token)
                    }
                    onSuccess.invoke()
                } else {
                    onError.invoke("Login failed")
                }
//                setLoading(false)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                setLoading(false)
                onError.invoke("Login error: ${t.message}")
            }
        })
    }

    fun getUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        apiService.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    _userResponse.value = responModel
                    val allergenIds = responModel?.data?.alergens?.map { it.id } ?: emptyList()
                    _userAllergenIds.value = allergenIds
                    onSuccess.invoke()
                } else {
                    onError.invoke("Get Users error")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onError.invoke("Get Users error: ${t.message}")
            }
        })
    }

    fun hasAllergens(): Boolean {
        return _userResponse.value?.data?.alergens?.isNotEmpty() ?: false
    }

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit) {
        apiService.logout().enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if (response.isSuccessful) {
                    sessionManager.clearSession()
                    onSuccess.invoke()
                } else {
                    onError.invoke("Logout error")
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                onError.invoke("Logout error: ${t.message}")
            }
        })
    }
}


