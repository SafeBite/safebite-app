package com.celvine.deb.esail.bby.data.model

data class Food(
    val id: Int,
    val name: String,
    val picture: String,
    val externalId: String,
    val ingredients: List<Ingredients>
)
