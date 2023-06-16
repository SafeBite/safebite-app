package com.celvine.deb.esail.bby.api

import android.media.Image
import com.celvine.deb.esail.bby.data.model.AccountVerificationOTP
import com.celvine.deb.esail.bby.data.model.FoodDetailResponse
import com.celvine.deb.esail.bby.data.model.FoodResponse
import com.celvine.deb.esail.bby.data.model.IngredientsResponse
import com.celvine.deb.esail.bby.data.model.LoginData
import com.celvine.deb.esail.bby.data.model.LoginResponse
import com.celvine.deb.esail.bby.data.model.LogoutResponse
import com.celvine.deb.esail.bby.data.model.PredictionResponse
import com.celvine.deb.esail.bby.data.model.ProfilePictureResponse
import com.celvine.deb.esail.bby.data.model.ResponModel
import com.celvine.deb.esail.bby.data.model.UpdateUserProfileRespone
import com.celvine.deb.esail.bby.data.model.UpdateUserRequest
import com.celvine.deb.esail.bby.data.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun getIngredients(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("mainAlergenOnly") mainAlergenOnly: Boolean): Call<IngredientsResponse>

    @GET("foods")
    fun getFoods(@Header("Authorization") token: String): Call<FoodResponse>

    @GET("foods/{id}")
    fun getFoodAllergic(@Header("Authorization") token: String): Call<FoodDetailResponse>

    @POST("users/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @Multipart
    @POST("foods/predict")
    fun predictFood(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<PredictionResponse>

    @PATCH("users/profile")
    fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ): Call<UpdateUserProfileRespone>

}