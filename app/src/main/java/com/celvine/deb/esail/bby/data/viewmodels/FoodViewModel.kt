package com.celvine.deb.esail.bby.data.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.FoodResponse
import com.celvine.deb.esail.bby.data.model.IngredientsData
import com.celvine.deb.esail.bby.data.model.IngredientsResponse
import com.celvine.deb.esail.bby.data.model.UpdateUserProfileRespone
import com.celvine.deb.esail.bby.data.model.UpdateUserRequest
import com.celvine.deb.esail.bby.data.model.UserResponse
import com.celvine.deb.esail.bby.data.repositories.FoodRepository
import com.celvine.deb.esail.bby.helper.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodViewModel(context: Context): ViewModel() {

    private val foodRepository: FoodRepository = FoodRepository()

    private val sessionManager: SessionManager = SessionManager(context)
    fun getIngredients(onSuccess: () -> Unit, onError: (String) -> Unit) {

        val apiService = ApiConfig.instanceRetrofit
        val getUserCall: Call<IngredientsResponse> = apiService.getIngredients(
            token = "Bearer ${sessionManager.fetchAuthToken()}",
            page = 1,
            limit = 10,
            mainAlergenOnly = true)

        getUserCall.enqueue(object : Callback<IngredientsResponse> {
            override fun onResponse(call: Call<IngredientsResponse>, response: Response<IngredientsResponse>) {

                if (response.isSuccessful) {
                    val responModel = response.body()
                    onSuccess.invoke()
                    if (responModel != null) {
                        foodRepository.ingredients = responModel.data.ingredients
                    }
                } else {

                    Log.d("Get Ingredients", "Get Ingredients error")
                }
            }

            override fun onFailure(call: Call<IngredientsResponse>, t: Throwable) {

                onError.invoke("Get Ingredients failed")
            }
        })
    }

    fun getFoods(onSuccess: () -> Unit, onError: (String) -> Unit) {

        val apiService = ApiConfig.instanceRetrofit
        val getUserCall: Call<FoodResponse> = apiService.getFoods(
            token = "Bearer ${sessionManager.fetchAuthToken()}")

        getUserCall.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {

                if (response.isSuccessful) {
                    val responModel = response.body()
                    onSuccess.invoke()
                    if (responModel != null) {
                    }
                } else {

                    Log.d("Get Ingredients", "Get Ingredients error")
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {

                onError.invoke("Get Ingredients failed")
            }
        })
    }

    fun updateUsers(
        request: UpdateUserRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {

        val apiService = ApiConfig.instanceRetrofit
        val getUserCall: Call<UpdateUserProfileRespone> = apiService.updateUser(
            token = "Bearer ${sessionManager.fetchAuthToken()}",
            request)

        getUserCall.enqueue(object : Callback<UpdateUserProfileRespone> {
            override fun onResponse(call: Call<UpdateUserProfileRespone>, response: Response<UpdateUserProfileRespone>) {

                if (response.isSuccessful) {
                    val responModel = response.body()
                    onSuccess.invoke()
                    if (responModel != null) {
                    }
                } else {

                    Log.d("updateUsers", "updateUsers error")
                }
            }

            override fun onFailure(call: Call<UpdateUserProfileRespone>, t: Throwable) {

                onError.invoke("updateUsers failed")
            }
        })
    }
}