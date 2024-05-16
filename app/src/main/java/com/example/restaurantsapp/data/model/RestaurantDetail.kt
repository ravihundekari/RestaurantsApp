package com.example.restaurantsapp.data.model

import com.google.gson.annotations.SerializedName


data class RestaurantDetail(
    @SerializedName("closed_bucket")
    val closedBucket: String,
    @SerializedName("geocodes")
    val geocodes: Geocodes,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String
)

data class Geocodes(
    @SerializedName("main")
    val main: Main?
)

data class Location(
    @SerializedName("formatted_address")
    val formattedAddress: String?,
)

data class Main(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)
