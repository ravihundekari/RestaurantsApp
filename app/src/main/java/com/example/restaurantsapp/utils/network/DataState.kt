package com.example.restaurantsapp.utils.network

sealed interface DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>
    data class Error(val exception: Throwable) : DataState<Nothing>
    object Loading : DataState<Nothing>
}