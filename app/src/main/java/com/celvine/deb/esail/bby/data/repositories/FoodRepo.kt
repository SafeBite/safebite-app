package com.celvine.deb.esail.bby.data.repositories

import android.util.Log
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.Food
import com.celvine.deb.esail.bby.data.model.FoodResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodRepo {

    var foods: List<Food> = emptyList()
    fun getFoods(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val apiService = ApiConfig.instanceRetrofit
        val getFoodsCall: Call<FoodResponse> = apiService.getFoods(page = 1, limit = 20)

        getFoodsCall.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    onSuccess.invoke()
                    if (responseModel != null) {
                        foods = responseModel.data.foods
                        Log.d("Get Foods", "Retrieved Foods")
                    }
                } else {
                    onError.invoke("Get Foods error")
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                onError.invoke("Get Foods failed: ${t.message}")
            }
        })
    }
}
