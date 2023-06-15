package com.celvine.deb.esail.bby.data.model

data class FoodResponse(
    val statusCode: String,
    val message: String,
    val data: FoodData,
    val meta: Meta
)
