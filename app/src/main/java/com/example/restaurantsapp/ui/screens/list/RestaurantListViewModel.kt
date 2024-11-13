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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
) : ViewModel() {

    private val _restaurantItems: MutableStateFlow<DataState<List<RestaurantItem>>> = MutableStateFlow(DataState.Loading)
    val restaurantItems : StateFlow<DataState<List<RestaurantItem>>>
        get() = _restaurantItems.asStateFlow()

    init {
        getNearbyRestaurants()
    }

    fun getNearbyRestaurants()  {
        viewModelScope.launch{
            restaurantRepository.getRestaurants().collect{
                _restaurantItems.value = it
            }
        }
    }

}


