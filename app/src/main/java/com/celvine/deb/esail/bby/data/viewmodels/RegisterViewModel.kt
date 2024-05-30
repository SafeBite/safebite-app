package com.celvine.deb.esail.bby.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
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
}
