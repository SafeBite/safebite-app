package com.celvine.deb.esail.bby.data.model

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("name")
    val name: String?,
    @SerializedName("alergens")
    val allergens: List<Int>
)
