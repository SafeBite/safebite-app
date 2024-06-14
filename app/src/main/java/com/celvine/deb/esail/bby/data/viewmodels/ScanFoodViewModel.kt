package com.celvine.deb.esail.bby.data.viewmodels

import ContentUriRequestBody
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanFoodViewModel(context: Context) : ViewModel() {
    private val contentResolver: ContentResolver = context.contentResolver
    var responModel: PredictionResponse? = null

    fun scanFood(imageUri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val fileRequestBody = ContentUriRequestBody(contentResolver, imageUri)

        val predictFoodCall: Call<PredictionResponse> = apiService.predictFood(
            file = MultipartBody.Part.createFormData(
                "file",
                imageUri.lastPathSegment,
                fileRequestBody
            ),
        )
        Log.d("ScanFoodViewModel", "Starting scan with URI: $imageUri")

        predictFoodCall.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    responModel = response.body()
                    Log.d("ScanFoodViewModel", "Response successful: ${response.body()}")
                    onSuccess.invoke()
                } else {
                    Log.e("ScanFoodViewModel", "Response error: ${response.errorBody()?.string()}")
                    onError.invoke("Scan failed with error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Log.e("ScanFoodViewModel", "Scan failed: ${t.message}", t)
                onError.invoke("Scan Food failed: ${t.message}")
            }
        })
    }
}
