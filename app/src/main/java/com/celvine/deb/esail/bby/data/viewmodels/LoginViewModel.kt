package com.celvine.deb.esail.bby.data.viewmodels

import ContentUriRequestBody
import android.content.ContentResolver
import android.content.Context
import android.media.Image
import android.net.Uri
import android.provider.MediaStore.Images.Media
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.model.LoginResponse
import com.celvine.deb.esail.bby.data.model.LogoutResponse
import com.celvine.deb.esail.bby.data.model.ProfilePictureResponse
import com.celvine.deb.esail.bby.data.model.ResponModel
import com.celvine.deb.esail.bby.data.model.UserResponse
import com.celvine.deb.esail.bby.helper.SessionManager
import com.celvine.deb.esail.bby.helper.uriToFile
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class LoginViewModel(context: Context) : ViewModel() {

    private val sessionManager: SessionManager = SessionManager(context)
    private val contentResolver: ContentResolver = context.contentResolver

    var userResponse: UserResponse? = null

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {

        val apiService = ApiConfig.instanceRetrofit
        val loginCall: Call<LoginResponse> = apiService.login(email, password)

        loginCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful) {
                    val responModel = response.body()
                    if (responModel != null) {
                        sessionManager.saveAuthToken(responModel.data.access.token)
                        Log.d("token", "Token: ${responModel.data.access.token}")
                    }
                    onSuccess.invoke()
                    if (responModel != null) {
                        Log.d("Login", "Login successful ${responModel.data.uuid}")
                    }
                } else {

                    Log.d("RegisterViewModel", "Registration error")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                onError.invoke("Login failed")
            }
        })
    }

    // Function to send the activation OTP
    fun sendActivationOTP(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Call the send activation OTP API endpoint using the ApiService from ApiConfig
        val apiService = ApiConfig.instanceRetrofit
        val sendOtpCall: Call<ResponModel> = apiService.sendActivationOTP(email)

        // Make an asynchronous API request
        sendOtpCall.enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                // Handle the response here
                if (response.isSuccessful) {
                    val responModel: ResponModel? = response.body()
                    if (responModel?.success == 1) {
                        // OTP sent successfully
                        onSuccess.invoke()
                    } else {
                        // Failed to send OTP
                        onError.invoke("Failed to send OTP")
                    }
                } else {
                    // Failed to send OTP
                    onError.invoke("Failed to send OTP")
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                // Handle the failure case
                onError.invoke("Failed to send OTP")
            }
        })
    }

    // Function to verify the activation OTP
    fun verifyActivationOTP(
        data: AccountVerificationOTP,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Call the verify activation OTP API endpoint using the ApiService from ApiConfig
        val apiService = ApiConfig.instanceRetrofit
        val verifyOtpCall: Call<ResponModel> = apiService.verifyActivationOTP(data)

        // Make an asynchronous API request
        verifyOtpCall.enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                // Handle the response here
                if (response.isSuccessful) {
                    val responModel: ResponModel? = response.body()
                    if (responModel?.success == 1) {
                        // OTP verification successful
                        onSuccess.invoke()
                    } else {
                        // OTP verification failed
                        onError.invoke("OTP verification failed")
                    }
                    onSuccess.invoke()
                } else {
                    // OTP verification failed
                    onError.invoke("OTP verification failed")
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                // Handle the failure case
                onError.invoke("OTP verification failed")
            }
        })
    }

    fun getUser(onSuccess: () -> Unit, onError: (String) -> Unit) {

        val apiService = ApiConfig.instanceRetrofit
        val getUserCall: Call<UserResponse> =
            apiService.getUsers(token = "Bearer ${sessionManager.fetchAuthToken()}")

        getUserCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {

                if (response.isSuccessful) {
                    val responModel = response.body()
                    userResponse = responModel
                    onSuccess.invoke()
                    if (responModel != null) {
                        Log.d("Get Users", "Get Users successful ${responModel.data}")
                    }
                } else {

                    Log.d("Get Users", "Get Users error")
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

        val fileRequestBody = ContentUriRequestBody(
            contentResolver,
            imageUri
        )
        val updateUserCall: Call<ProfilePictureResponse> = apiService.uploadProfilePicture(
            token = "Bearer ${sessionManager.fetchAuthToken()}",
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

    }

    fun logout(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val logoutCall: Call<LogoutResponse> =
            apiService.logout(token = "Bearer ${sessionManager.fetchAuthToken()}")

        logoutCall.enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                if (response.isSuccessful) {
                    // Logout successful
                    onSuccess.invoke()
                } else {
                    // Failed to logout
                    onError.invoke("Logout failed")
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                // Handle the failure case
                onError.invoke("Logout failed")
            }
        })
    }


//    fun getUserResponse(): UserResponse? {
//        return userResponse
//    }
}


