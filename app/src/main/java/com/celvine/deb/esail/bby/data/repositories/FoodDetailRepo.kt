package com.celvine.deb.esail.bby.data.repositories

import com.celvine.deb.esail.bby.api.ApiService
import com.celvine.deb.esail.bby.data.model.FoodIdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FoodDetailRepo(private val apiService: ApiService) {
    suspend fun getFoodDetail(id: Int): Response<FoodIdResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getFoodDetail(id).execute()
        }
    }
}
