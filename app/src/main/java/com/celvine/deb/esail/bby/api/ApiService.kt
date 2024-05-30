package com.celvine.deb.esail.bby.api

import com.celvine.deb.esail.bby.data.model.*
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
    fun getUsers(): Call<UserResponse>

    @POST("users/refresh")
    fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Call<RefreshAccessToken>

    @Multipart
    @POST("users/profile/upload")
    fun uploadProfilePicture(
        @Part file: MultipartBody.Part
    ): Call<ProfilePictureResponse>

    @GET("foods/ingredients")
    fun getIngredients(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("mainAlergenOnly") mainAlergenOnly: Boolean
    ): Call<IngredientsResponse>

    @GET("foods")
    fun getFoods(@Query("page") page: Int, @Query("limit") limit: Int): Call<FoodResponse>

    @GET("foods/{id}")
    fun getFoodDetail(@Path("id") id: Int): Call<FoodIdResponse>

    @POST("users/logout")
    fun logout(): Call<LogoutResponse>

    @Multipart
    @POST("foods/predict")
    fun predictFood(
        @Part file: MultipartBody.Part
    ): Call<PredictionResponse>

    @PATCH("users/profile")
    fun updateUser(
        @Body request: UpdateUserRequest
    ): Call<UpdateUserProfileRespone>
}
