package com.celvine.deb.esail.bby.data.model

data class Ingredient(
    val id: Int,
    val name: String,
    val icon: String?,
    val isMainAlergen: Boolean,
    val userAlergic: Int,
    val createdAt: String,
    val updatedAt: String
)
