package com.example.restaurantsapp.domain.usecase

import com.example.restaurantsapp.data.repositories.RestaurantRepositoryImpl
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRestaurantsUseCase(private val repository: RestaurantRepositoryImpl) {
    operator fun invoke(): Flow<DataState<List<RestaurantItem>>> = flow {
        repository.getRestaurants().collect {
            when (it) {
                is DataState.Error -> emit(DataState.Error(it.message))

                DataState.Loading -> emit(DataState.Loading)

                is DataState.Success -> {
                    val data = it.data
                    data.forEach { item ->
                        repository.getRestaurantPhotos(item.fsqId).collect { restaurantImages ->
                            when (restaurantImages) {
                                is DataState.Error -> {}

                                is DataState.Loading -> {}

                                is DataState.Success -> {
                                    item.restaurantsPhotos = restaurantImages.data
                                }
                            }
                        }
                    }
                    emit(DataState.Success(data))
                }
            }
        }
    }
}