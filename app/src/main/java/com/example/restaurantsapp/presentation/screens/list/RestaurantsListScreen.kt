package com.example.restaurantsapp.presentation.screens.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.restaurantsapp.R
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.presentation.component.CircularIndeterminateProgressBar
import com.example.restaurantsapp.presentation.component.RestaurantAlertDialog
import com.example.restaurantsapp.presentation.component.RestaurantItemList
import com.example.restaurantsapp.utils.network.DataState

@Composable
fun RestaurantsListScreen(navController: NavController, ) {
    val restaurantsViewModel: RestaurantListViewModel = hiltViewModel()
    val context = LocalContext.current

    val state by restaurantsViewModel.restaurantItems.collectAsStateWithLifecycle()
    var isLoading by rememberSaveable { mutableStateOf(true) }

    when (state){
       is DataState.Success->{
           RestaurantItemList(
               navController = navController,
               restaurantItemList = (state as DataState.Success<List<RestaurantItem>>).data
           )
           isLoading = false
       }

        is DataState.Error -> {
            RestaurantAlertDialog(
                dialogTitle = context.getString(R.string.restaurant_name),
                dialogText = (state as DataState.Error).message.toString(),
                icon = Icons.Default.Info
            ) {
                restaurantsViewModel.getNearbyRestaurants()
            }
            isLoading = false
        }
        DataState.Loading -> {
            CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.4f)
        }
    }
}