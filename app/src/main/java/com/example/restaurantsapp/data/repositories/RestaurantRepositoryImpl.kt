package com.example.restaurantsapp.data.repositories

import com.example.restaurantsapp.data.datasource.remote.ApiService
import com.example.restaurantsapp.domain.model.RestaurantDetail
import com.example.restaurantsapp.domain.model.RestaurantImageItem
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.domain.repositories.RestaurantRepository
import com.example.restaurantsapp.utils.Query.DISTANCE
import com.example.restaurantsapp.utils.Query.PLACE
import com.example.restaurantsapp.utils.Query.RESTAURANT_DETAILS_FIELDS
import com.example.restaurantsapp.utils.Query.RESTAURANT_LIST_FIELDS
import com.example.restaurantsapp.utils.network.DataState
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class RestaurantRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RestaurantRepository {
    override suspend fun getRestaurants(): Flow<DataState<List<RestaurantItem>>> = flow {
        with(apiService.getRestaurants(PLACE, DISTANCE, RESTAURANT_LIST_FIELDS)) {
            if (isSuccessful) {
                body()?.let {
                    emit(DataState.Success(it.restaurantItemList))
                }
            } else {
                emit(DataState.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(DataState.Error(it.localizedMessage))
    }

    override suspend fun getRestaurantPhotos(fsqId: String): Flow<DataState<List<RestaurantImageItem>>> =
        flow {
            with(apiService.getRestaurantPhotos(fsqId)) {
                if (isSuccessful) {
                    body()?.let {
                        emit(DataState.Success(it))
                    }
                } else {
                    emit(DataState.Error(this.errorBody().toString()))
                }
            }
        }.catch {
            emit(DataState.Error(it.localizedMessage))
        }

    override suspend fun getRestaurantDetails(fsqId: String): Flow<DataState<RestaurantDetail>> =
        flow {
            with(apiService.getRestaurantDetails(fsqId, RESTAURANT_DETAILS_FIELDS)) {
                if (isSuccessful) {
                    body()?.let {
                        emit(DataState.Success(it))
                    }
                } else {
                    emit(DataState.Error(this.errorBody().toString()))
                }
            }
        }.catch { emit(DataState.Error(it.localizedMessage)) }

}