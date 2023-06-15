package com.celvine.deb.esail.bby.data.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.ProfilePictureResponse
import com.celvine.deb.esail.bby.helper.SessionManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ScanFoodViewModel(context: Context): ViewModel() {
    private val sessionManager: SessionManager = SessionManager(context)
    private var photo: File? = null
    private lateinit var photoPaths: String

    fun scanFood(imageUri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val file = File(imageUri.path) // Assuming the imageUri is a valid file URI

        val requestFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val imageBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val getUserCall: Call<ProfilePictureResponse> = apiService.uploadProfilePicture(
            token = "Bearer ${sessionManager.fetchAuthToken()}",
            file = imageBody
        )

        getUserCall.enqueue(object : Callback<ProfilePictureResponse> {
            override fun onResponse(call: Call<ProfilePictureResponse>, response: Response<ProfilePictureResponse>) {
                if (response.isSuccessful) {
                    val responModel = response.body()
                    onSuccess.invoke()
                    if (responModel != null) {
                        Log.d("Scan Food", "Scan Food successful ${responModel.message}")
                    }
                } else {
                    Log.d("Scan Food", "Scan Food error")
                }
            }

            override fun onFailure(call: Call<ProfilePictureResponse>, t: Throwable) {
                onError.invoke("Scan Food failed")
            }
        })
    }

}