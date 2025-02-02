package com.example.restaurantsapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.domain.usecase.GetRestaurantsUseCase
import com.example.restaurantsapp.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val restaurantsUseCase: GetRestaurantsUseCase,
) : ViewModel() {

    private val _restaurantItems: MutableStateFlow<DataState<List<RestaurantItem>>> =
        MutableStateFlow(DataState.Loading)
    val restaurantItems: StateFlow<DataState<List<RestaurantItem>>>
        get() = _restaurantItems.asStateFlow()

    init {
        getNearbyRestaurants()
    }

    fun getNearbyRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            restaurantsUseCase().collect {
                _restaurantItems.value = it
            }
        }
    }
}


