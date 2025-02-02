package com.example.restaurantsapp.data.datasource.remote

import com.example.restaurantsapp.domain.model.RestaurantDetail
import com.example.restaurantsapp.domain.model.RestaurantImageItem
import com.example.restaurantsapp.domain.model.RestaurantItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v3/places/search")
    suspend fun getRestaurants(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("fields") fields: String
    ): Response<RestaurantItems>

    @GET("v3/places/{fsq_id}/photos")
    suspend fun getRestaurantPhotos(@Path("fsq_id") fsqId: String): Response<List<RestaurantImageItem>>

    @GET("v3/places/{fsq_id}")
    suspend fun getRestaurantDetails(
        @Path("fsq_id") fsqId: String,
        @Query("fields") fields: String
    ): Response<RestaurantDetail>
}