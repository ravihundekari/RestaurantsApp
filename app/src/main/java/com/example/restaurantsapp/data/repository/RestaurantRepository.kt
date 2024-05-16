package com.example.restaurantsapp.data.repository

import com.example.restaurantsapp.data.datasource.remote.ApiService
import com.example.restaurantsapp.data.model.RestaurantDetail
import com.example.restaurantsapp.data.model.RestaurantImageItem
import com.example.restaurantsapp.data.model.RestaurantItems
import com.example.restaurantsapp.utils.Query.DISTANCE
import com.example.restaurantsapp.utils.Query.PLACE
import com.example.restaurantsapp.utils.Query.RESTAURANT_DETAILS_FIELDS
import com.example.restaurantsapp.utils.Query.RESTAURANT_LIST_FIELDS
import com.example.restaurantsapp.utils.network.DataState
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class RestaurantRepository @Inject constructor(private val apiService: ApiService) :
    RestaurantRepositoryInterface {
    override suspend fun getRestaurants(): Flow<DataState<RestaurantItems>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult =
                apiService.getRestaurants(PLACE, DISTANCE, RESTAURANT_LIST_FIELDS)
            emit(DataState.Success(searchResult))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun getRestaurantPhotos(fsqId: String): Flow<DataState<List<RestaurantImageItem>>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult = apiService.getRestaurantPhotos(fsqId)
                emit(DataState.Success(searchResult))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    override suspend fun getRestaurantDetails(fsqId: String): Flow<DataState<RestaurantDetail>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult =
                    apiService.getRestaurantDetails(fsqId, RESTAURANT_DETAILS_FIELDS)
                emit(DataState.Success(searchResult))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

}