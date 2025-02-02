package com.example.restaurantsapp.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantsapp.domain.model.RestaurantDetail
import com.example.restaurantsapp.domain.usecase.GetRestaurantDetailsUseCase
import com.example.restaurantsapp.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val getRestaurantDetailsUseCase: GetRestaurantDetailsUseCase,
) : ViewModel() {

    val _restaurantDetail: MutableStateFlow<DataState<RestaurantDetail>> =
        MutableStateFlow(DataState.Loading)
    val restaurantDetail: StateFlow<DataState<RestaurantDetail>>
        get() = _restaurantDetail.asStateFlow()


    fun getRestaurantDetails(fsqId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getRestaurantDetailsUseCase(fsqId).collect {
                    _restaurantDetail.value = it
                }
            }
        }
    }

}