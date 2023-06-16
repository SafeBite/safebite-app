package com.celvine.deb.esail.bby.data.viewmodels

import ContentUriRequestBody
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.PredictionResponse
import com.celvine.deb.esail.bby.data.model.ProfilePictureResponse
import com.celvine.deb.esail.bby.helper.SessionManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ScanFoodViewModel(context: Context) : ViewModel() {
    private val sessionManager: SessionManager = SessionManager(context)
    private val contentResolver: ContentResolver = context.contentResolver

    private var photo: File? = null
    private lateinit var photoPaths: String

    fun scanFood(imageUri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {

        val apiService = ApiConfig.instanceRetrofit

        val fileRequestBody = ContentUriRequestBody(contentResolver, imageUri)

        val getUserCall: Call<PredictionResponse> = apiService.predictFood(
            token = "Bearer ${sessionManager.fetchAuthToken()}",
            file = MultipartBody.Part.createFormData(
                "file",
                imageUri.lastPathSegment,
                fileRequestBody
            )
        )

        getUserCall.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
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

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                onError.invoke("Scan Food failed")
            }
        })
    }

}