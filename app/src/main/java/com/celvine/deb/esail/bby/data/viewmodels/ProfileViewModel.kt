//package com.celvine.deb.esail.bby.data.viewmodels
//
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import com.celvine.deb.esail.bby.api.ApiConfig
//import com.celvine.deb.esail.bby.data.model.ResponModel
//import com.celvine.deb.esail.bby.data.model.UserResponse
//import com.celvine.deb.esail.bby.helper.SessionManager
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ProfileViewModel(): ViewModel(){
//    private val sessionManager: SessionManager = SessionManager(context)
//    fun getUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
//
//        val apiService = ApiConfig.instanceRetrofit
//        val getUserCall: Call<UserResponse> = apiService.getUsers(token = "Bearer ${sessionManager.fetchAuthToken()}")
//
//        getUserCall.enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//
//                if (response.isSuccessful) {
//                    val responModel = response.body()
//
//                    onSuccess.invoke()
//                    Log.d("Get Users", "Get Users successful")
//                } else {
//
//                    Log.d("Get Users", "Get Users error")
//                }
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//
//                onError.invoke("Get Users failed")
//            }
//        })
//    }
//}