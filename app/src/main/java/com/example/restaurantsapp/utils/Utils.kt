package com.example.restaurantsapp.utils

import android.content.Context
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.restaurantsapp.R
import kotlinx.coroutines.Dispatchers

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
}