package com.example.restaurantsapp.data.repository

import com.example.restaurantsapp.data.model.RestaurantDetail
import com.example.restaurantsapp.data.model.RestaurantImageItem
import com.example.restaurantsapp.data.model.RestaurantItem
import com.example.restaurantsapp.data.model.RestaurantItems
import com.example.restaurantsapp.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface RestaurantRepositoryInterface {
    suspend fun getRestaurants(): Flow<DataState<List<RestaurantItem>>>
    suspend fun getRestaurantPhotos(fsqId: String): Flow<DataState<List<RestaurantImageItem>>>
    suspend fun getRestaurantDetails(fsqId: String): Flow<DataState<RestaurantDetail>>
}