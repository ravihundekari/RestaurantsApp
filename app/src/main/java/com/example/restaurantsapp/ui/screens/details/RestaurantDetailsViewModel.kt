package com.example.restaurantsapp.ui.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.data.model.RestaurantDetail
import com.example.restaurantsapp.data.repository.RestaurantRepository
import com.example.restaurantsapp.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
) : ViewModel() {

    val restaurantDetail: MutableState<RestaurantDetail?> = mutableStateOf(null)
    val isLoading: MutableState<Boolean> = mutableStateOf(true)
    val error: MutableState<String?> = mutableStateOf(null)

    fun getRestaurantDetails(fsqId: String) {
        viewModelScope.launch {
            restaurantRepository.getRestaurantDetails(fsqId).collect {
                when (it) {
                    is DataState.Error -> {
                        isLoading.value = false
                        error.value = it.exception.message
                    }

                    DataState.Loading -> {
                        isLoading.value = true
                        error.value = null
                    }

                    is DataState.Success -> {
                        restaurantDetail.value = it.data
                        isLoading.value = false
                        error.value = null
                    }
                }

            }
        }
    }

}