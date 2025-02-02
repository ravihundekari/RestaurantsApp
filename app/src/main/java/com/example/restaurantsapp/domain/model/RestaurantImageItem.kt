package com.example.restaurantsapp.domain.model

import com.google.gson.annotations.SerializedName

data class RestaurantImageItem(
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("suffix")
    val suffix: String
)