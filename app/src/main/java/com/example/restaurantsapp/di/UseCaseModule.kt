package com.example.restaurantsapp.di

import com.example.restaurantsapp.data.repositories.RestaurantRepositoryImpl
import com.example.restaurantsapp.domain.usecase.GetRestaurantDetailsUseCase
import com.example.restaurantsapp.domain.usecase.GetRestaurantsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetRestaurantUseCase(
        restaurantRepositoryImpl: RestaurantRepositoryImpl,
    ): GetRestaurantsUseCase {
        return GetRestaurantsUseCase(restaurantRepositoryImpl)
    }

    @Singleton
    @Provides
    fun provideGetRestaurantDetailsUseCase(
        restaurantRepositoryImpl: RestaurantRepositoryImpl,
    ): GetRestaurantDetailsUseCase {
        return GetRestaurantDetailsUseCase(restaurantRepositoryImpl)
    }
}