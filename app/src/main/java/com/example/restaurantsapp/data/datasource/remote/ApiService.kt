package com.example.restaurantsapp.data.datasource.remote

import com.example.restaurantsapp.data.model.RestaurantDetail
import com.example.restaurantsapp.data.model.RestaurantImageItem
import com.example.restaurantsapp.data.model.RestaurantItems
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v3/places/search")
    suspend fun getRestaurants(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("fields") fields: String
    ): RestaurantItems

    @GET("v3/places/{fsq_id}/photos")
    suspend fun getRestaurantPhotos(@Path("fsq_id") fsqId: String): List<RestaurantImageItem>

    @GET("v3/places/{fsq_id}")
    suspend fun getRestaurantDetails(
        @Path("fsq_id") fsqId: String,
        @Query("fields") fields: String
    ): RestaurantDetail
}