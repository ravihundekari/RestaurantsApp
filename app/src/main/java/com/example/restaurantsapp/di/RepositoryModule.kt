package com.example.restaurantsapp.di

import com.example.restaurantsapp.data.datasource.remote.ApiService
import com.example.restaurantsapp.data.repositories.RestaurantRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRestaurantRepository(
        apiService: ApiService,
    ): RestaurantRepositoryImpl {
        return RestaurantRepositoryImpl(apiService)
    }

}