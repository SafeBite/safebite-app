package com.celvine.deb.esail.bby.data.model

data class PredictionResponse(
    val statusCode: String,
    val message: String,
    val data: PredictionData
)
