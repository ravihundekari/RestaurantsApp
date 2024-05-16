package com.example.restaurantsapp.di

import com.example.restaurantsapp.data.datasource.remote.ApiService
import com.example.restaurantsapp.utils.Constants.API_URL
import com.example.restaurantsapp.utils.Constants.HEADER_ACCEPT
import com.example.restaurantsapp.utils.Constants.HEADER_ACCEPT_VALUE
import com.example.restaurantsapp.utils.Constants.HEADER_AUTH
import com.example.restaurantsapp.utils.Constants.HEADER_AUTH_VALUE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl(): String = API_URL

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor {
                val builder = it.request().newBuilder()
                builder.header(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
                builder.header(HEADER_AUTH, HEADER_AUTH_VALUE)
                return@Interceptor it.proceed(builder.build())
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRestApiService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java
    )
}