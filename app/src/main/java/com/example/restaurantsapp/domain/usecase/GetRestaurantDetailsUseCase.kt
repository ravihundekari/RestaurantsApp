package com.example.restaurantsapp.domain.usecase

import com.example.restaurantsapp.domain.model.RestaurantDetail
import com.example.restaurantsapp.data.repositories.RestaurantRepositoryImpl
import com.example.restaurantsapp.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRestaurantDetailsUseCase(private val repositoryImpl: RestaurantRepositoryImpl) {
    operator fun invoke(fsqId: String) : Flow<DataState<RestaurantDetail>> = flow {
        repositoryImpl.getRestaurantDetails(fsqId).collect {
            when (it) {
                is DataState.Error -> emit(DataState.Error(it.message))
                DataState.Loading -> emit(DataState.Loading)
                is DataState.Success -> emit(DataState.Success(it.data))
            }
        }
    }
}