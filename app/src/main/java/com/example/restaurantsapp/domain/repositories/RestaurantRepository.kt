package com.example.restaurantsapp.domain.repositories

import com.example.restaurantsapp.domain.model.RestaurantDetail
import com.example.restaurantsapp.domain.model.RestaurantImageItem
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun getRestaurants(): Flow<DataState<List<RestaurantItem>>>
    suspend fun getRestaurantPhotos(fsqId: String): Flow<DataState<List<RestaurantImageItem>>>
    suspend fun getRestaurantDetails(fsqId: String): Flow<DataState<RestaurantDetail>>
}