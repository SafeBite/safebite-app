package com.celvine.deb.esail.bby.data.viewmodels

import ContentUriRequestBody
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.*
import com.celvine.deb.esail.bby.helper.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context) : ViewModel() {
    private val sessionManager: SessionManager = SessionManager(context)
    private val contentResolver: ContentResolver = context.contentResolver

    private val _userAllergenIds = MutableLiveData<List<Int>>()
    val userAllergenIds: LiveData<List<Int>> = _userAllergenIds

    private val _userResponse = MutableLiveData<UserResponse?>()
    val userResponse: LiveData<UserResponse?> get() = _userResponse

    fun performLogin(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val loginCall: Call<LoginResponse> = apiService.login(email, password)

        loginCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    if (responModel != null) {
                        sessionManager.saveAuthToken(
                            responModel.data.access.token,
                            responModel.data.access.expiredAt
                        )
                        sessionManager.saveRefreshToken(responModel.data.refresh.token)
                        Log.d("token", "Token: ${responModel.data.access.token}")
                    }
                    onSuccess.invoke()
                    if (responModel != null) {
                        Log.d("Login", "Login successful ${responModel.data.uuid}")
                    }
                } else {
                    onError.invoke("Login failed")
                    Log.d("LoginViewModel", "Login error")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onError.invoke("Login failed")
            }
        })
    }

    fun getUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val token = sessionManager.fetchAuthToken()
        Log.d("Get User", "Token: $token")
        val getUserCall: Call<UserResponse> = apiService.getUsers()
        Log.d("Get User", "Token: $token")
        getUserCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    _userResponse.value = responModel
                    onSuccess.invoke()
                    if (responModel != null) {
                        val allergenIds = responModel.data?.alergens?.map { it.id } ?: emptyList()
                        _userAllergenIds.value = allergenIds
                        Log.d("Get Users", "Get Users successful ${responModel.data}")
                    }
                } else {
                    onError.invoke("Get Users error")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onError.invoke("Get Users failed")
            }
        })
    }

    fun uploadProfilePicture(imageUri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.v("uploadProfilePicture", "uploadProfilePicture called")
        Log.v("uploadProfilePicture", "imageUri: ${imageUri}")

        val apiService = ApiConfig.instanceRetrofit
        val token = sessionManager.fetchAuthToken()

        if (token != null) {
            val fileRequestBody = ContentUriRequestBody(contentResolver, imageUri)
            val updateUserCall: Call<ProfilePictureResponse> = apiService.uploadProfilePicture(
                file = MultipartBody.Part.createFormData(
                    "file",
                    imageUri.lastPathSegment,
                    fileRequestBody
                )
            )

            updateUserCall.enqueue(object : Callback<ProfilePictureResponse> {
                override fun onResponse(
                    call: Call<ProfilePictureResponse>,
                    response: Response<ProfilePictureResponse>
                ) {
                    if (response.isSuccessful) {
                        val responModel = response.body()
                        onSuccess.invoke()
                        if (responModel != null) {
                            Log.d(
                                "UploadProfilePicture",
                                "Upload Profile Picture successful ${responModel.message}"
                            )
                        }
                    } else {
                        Log.d("UploadProfilePicture", "Upload Profile Picture error")
                    }
                }

                override fun onFailure(call: Call<ProfilePictureResponse>, t: Throwable) {
                    onError.invoke("Upload Profile Picture failed")
                }
            })
        } else {
            onError.invoke("No token found")
        }
    }

    fun hasAllergens(): Boolean {
        return _userResponse.value?.data?.alergens?.isNotEmpty() ?: false
    }

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val token = sessionManager.fetchAuthToken()
            val logoutCall: Call<LogoutResponse> = apiService.logout()

            logoutCall.enqueue(object : Callback<LogoutResponse> {
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    if (response.isSuccessful) {
                        sessionManager.clearSession()
                        onSuccess.invoke()
                    } else {
                        onError.invoke("Logout failed")
                    }
                }
                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    onError.invoke("Logout failed")
                }
            })
    }

}
