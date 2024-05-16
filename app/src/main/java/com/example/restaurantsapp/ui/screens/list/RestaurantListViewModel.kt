package com.example.restaurantsapp.ui.screens.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.data.model.RestaurantItem
import com.example.restaurantsapp.data.repository.RestaurantRepository
import com.example.restaurantsapp.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
) : ViewModel() {

    val restaurantItems: MutableState<List<RestaurantItem>?> = mutableStateOf(null)
    val isLoading: MutableState<Boolean> = mutableStateOf(true)
    val error: MutableState<String?> = mutableStateOf(null)

    fun getNearbyRestaurants() = viewModelScope.launch {
        async { restaurantRepository.getRestaurants() }.await().collect { restaurantsItem ->
            when (restaurantsItem) {
                is DataState.Error -> {
                    error.value = restaurantsItem.exception.message
                    isLoading.value = false
                }

                DataState.Loading -> {
                    error.value = null
                    isLoading.value = true
                }

                is DataState.Success -> {
                    val list: List<RestaurantItem> =
                        restaurantsItem.data.restaurantItemList
                    list.forEach { item ->
                        try {
                            async { restaurantRepository.getRestaurantPhotos(item.fsqId) }.await()
                                .collect { restaurantImages ->
                                    when (restaurantImages) {
                                        is DataState.Error -> {}

                                        DataState.Loading -> {}

                                        is DataState.Success -> {
                                            item.restaurantsPhotos = restaurantImages.data
                                        }
                                    }
                                }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    restaurantItems.value = list
                    isLoading.value = false
                    error.value = null
                }
            }
        }
    }
}


