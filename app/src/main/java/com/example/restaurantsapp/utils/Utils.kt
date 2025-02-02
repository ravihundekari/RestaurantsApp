package com.example.restaurantsapp.utils

import android.content.Context
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.restaurantsapp.R
import com.example.restaurantsapp.utils.network.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

object Utils {
    fun imageRequest(
        context: Context,
        imageUrl: String
    ): ImageRequest {
        val placeholder = R.drawable.ic_launcher_background

        val listener = object : ImageRequest.Listener {
            override fun onError(request: ImageRequest, result: ErrorResult) {
                super.onError(request, result)
            }

            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                super.onSuccess(request, result)
            }
        }

        val imageRequest = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .listener(listener)
            .dispatcher(Dispatchers.IO)
            .memoryCacheKey(imageUrl)
            .diskCacheKey(imageUrl)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
        return imageRequest
    }

    fun <T> Flow<T>.asResult(): Flow<DataState<T>> {
        return this
            .map<T, DataState<T>> { DataState.Success(it) }
            .onStart { emit(DataState.Loading) }
            .catch { emit(DataState.Error(it.message)) }
    }
}