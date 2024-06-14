package com.celvine.deb.esail.bby.data.model

import com.google.gson.annotations.SerializedName

data class UpdateUserNameRequest(
    @SerializedName("name")
    val name: String?
)
