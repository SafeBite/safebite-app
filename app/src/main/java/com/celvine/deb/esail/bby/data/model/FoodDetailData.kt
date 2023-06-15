package com.celvine.deb.esail.bby.data.model

data class FoodDetailData(
    val id: Int,
    val name: String,
    val picture: String,
    val externalId: String,
    val description: String,
    val alergic: List<Alergic>,
    val ingredients: List<Ingredients>
)
