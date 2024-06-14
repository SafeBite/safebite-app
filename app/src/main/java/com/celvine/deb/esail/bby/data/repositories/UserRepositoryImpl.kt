//package com.celvine.deb.esail.bby.data.repositories
//
//import com.celvine.deb.esail.bby.api.ApiConfig
//import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
//import com.celvine.deb.esail.bby.data.model.LoginResponse
//import com.celvine.deb.esail.bby.data.model.LogoutResponse
//import com.celvine.deb.esail.bby.data.model.ResponModel
//import com.celvine.deb.esail.bby.data.model.UserResponse
//import retrofit2.Call
//
////class UserRepositoryImpl : UserRepository {
////    private val apiService = ApiConfig.instanceRetrofit
////
////    override fun login(email: String, password: String): Call<LoginResponse> {
////        return apiService.login(email, password)
////    }
////
////    override fun getUsers(): Call<UserResponse> {
////        return apiService.getUsers()
////    }
////
////    override fun logout(): Call<LogoutResponse> {
////        return apiService.logout()
////    }
////
////    override fun register(name: String, email: String, password: String): Call<ResponModel> {
////        return apiService.register(name, email, password)
////    }
////
////    override fun sendActivationOTP(email: String): Call<ResponModel> {
////        return apiService.sendActivationOTP(email)
////    }
////
////    override fun verifyActivationOTP(data: AccountVerificationOTP): Call<ResponModel> {
////        return apiService.verifyActivationOTP(data)
////    }
////}
