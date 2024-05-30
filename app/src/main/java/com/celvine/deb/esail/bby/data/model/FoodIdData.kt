package com.celvine.deb.esail.bby.data.model

data class FoodIdData(
    val id: Int,
    val name: String,
    val picture: String,
    val externalId: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val alergic: List<Alergic>
)
