package com.celvine.deb.esail.bby.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.RegistrationRequest
import com.celvine.deb.esail.bby.data.model.RegistrationResponse
import com.celvine.deb.esail.bby.data.model.ResponModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    fun registerUser(name: String, email: String, password: String,onSuccess: () -> Unit, onError: (String) -> Unit  ) {
        // Make the API call to register the user
        val apiService = ApiConfig.instanceRetrofit
        val call = apiService.register(name, email, password)

        // Handle the response asynchronously
        call.enqueue(object : retrofit2.Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful) {
                    // Registration successful
                    val responModel = response.body()
                    if (responModel?.success == 1) {
                        // OTP sent successfully
                        onSuccess.invoke()
                    } else {
                        // Failed to send OTP
                        onError.invoke("Failed to send OTP")
                    }
                    // Handle the response as needed
                    onSuccess.invoke()
                    Log.d("RegisterViewModel", "Registration successful")
                } else {
                    // Registration failed
                    // Handle the error response
                    Log.d("RegisterViewModel", "Registration error")
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                // Registration failed due to network error
                // Handle the failure
            }
        })
    }
}
