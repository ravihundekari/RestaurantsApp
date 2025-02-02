package com.example.restaurantsapp.domain.model

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class RestaurantItems(
    @SerializedName("results") val restaurantItemList: List<RestaurantItem>
)

data class RestaurantItem(
    @SerializedName("closed_bucket") val closedBucket: String,
    @SerializedName("fsq_id") val fsqId: String,
    @SerializedName("name") val name: String,
    var restaurantsPhotos: List<RestaurantImageItem>
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}
