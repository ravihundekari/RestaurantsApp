package com.example.restaurantsapp.domain.model

import com.example.restaurantsapp.utils.JsonNavType
import com.google.gson.Gson

class RestaurantItemArgType : JsonNavType<RestaurantItem>() {
    override fun fromJsonParse(value: String): RestaurantItem =
        Gson().fromJson(value, RestaurantItem::class.java)

    override fun RestaurantItem.getJsonParse(): String = Gson().toJson(this)
}