package com.celvine.deb.esail.bby.data.viewmodels

import ContentUriRequestBody
import android.content.ContentResolver
import androidx.compose.runtime.mutableStateListOf
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(context: Context) : ViewModel() {
    private val _selectedIconIds = mutableStateListOf<Int>()
    val selectedIconIds: List<Int> get() = _selectedIconIds
    private val contentResolver: ContentResolver = context.contentResolver
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun updateUsers(
        request: UpdateUserRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = ApiConfig.instanceRetrofit
            val updateUserCall: Call<UpdateUserProfileResponse> = apiService.updateUser( request)

            updateUserCall.enqueue(object : Callback<UpdateUserProfileResponse> {
                override fun onResponse(call: Call<UpdateUserProfileResponse>, response: Response<UpdateUserProfileResponse>) {
                    if (response.isSuccessful) {
                        val responModel = response.body()
                        onSuccess.invoke()
                        if (responModel != null) {
                            Log.d("Update Users", "Update Users successful")
                        }
                    } else {
                        onError.invoke("Update Users error")
                    }
                }
                override fun onFailure(call: Call<UpdateUserProfileResponse>, t: Throwable) {
                    onError.invoke("Update Users failed")
                }
            })
    }

    fun updateUsersName(
        request: UpdateUserNameRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = ApiConfig.instanceRetrofit
        val updateUserCall: Call<UpdateUserProfileResponse> = apiService.updateUserName( request)

        updateUserCall.enqueue(object : Callback<UpdateUserProfileResponse> {
            override fun onResponse(call: Call<UpdateUserProfileResponse>, response: Response<UpdateUserProfileResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    onSuccess.invoke()
                    if (responModel != null) {
                        Log.d("Update Users", "Update Users successful")
                    }
                } else {
                    onError.invoke("Update Users error")
                }
            }
            override fun onFailure(call: Call<UpdateUserProfileResponse>, t: Throwable) {
                onError.invoke("Update Users failed")
            }
        })
    }

    fun addIconId(id: Int) {
        if (id !in _selectedIconIds) {
            _selectedIconIds.add(id)
        }
    }

    fun removeIconId(id: Int) {
        _selectedIconIds.remove(id)
    }

    fun clearSelectedIcons() {
        _selectedIconIds.clear()
    }

    fun setInitialSelectedIcons(allergenIds: List<Int>) {
        _selectedIconIds.clear()
        _selectedIconIds.addAll(allergenIds)
    }
    fun uploadProfilePicture(imageUri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.v("uploadProfilePicture", "uploadProfilePicture called")
        Log.v("uploadProfilePicture", "imageUri: ${imageUri}")

        val apiService = ApiConfig.instanceRetrofit
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
    }
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
    fun resetImageUri() {
        _imageUri.value = null
    }
}
