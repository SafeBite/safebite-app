package com.celvine.deb.esail.bby.api

import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.model.LoginData
import com.celvine.deb.esail.bby.data.model.LoginResponse
import com.celvine.deb.esail.bby.data.model.LogoutResponse
import com.celvine.deb.esail.bby.data.model.ProfilePictureResponse
import com.celvine.deb.esail.bby.data.model.ResponModel
import com.celvine.deb.esail.bby.data.model.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("users/signup")
    fun register(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("users/login")
    fun login(
            @Field("email") email: String,
            @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("users/activate/send")
    fun sendActivationOTP(
        @Field("email") email: String
    ): Call<ResponModel>

    @POST("users/activate")
    fun verifyActivationOTP(
        @Body data: AccountVerificationOTP
    ): Call<ResponModel>

    @GET("users")
    fun getUsers(@Header("Authorization") token: String): Call<UserResponse>

    @Multipart
    @POST("users/profile/upload")
    fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<ProfilePictureResponse>

    @GET("foods/ingredients")
    fun getIngredients(@Header("Authorization") token: String): Call<UserResponse>

    @GET("foods")
    fun getFoods(@Header("Authorization") token: String): Call<UserResponse>

    @GET("foods/{id}")
    fun getFoodAllergic(@Header("Authorization") token: String): Call<UserResponse>

    @POST("users/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>
}