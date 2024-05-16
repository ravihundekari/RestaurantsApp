package com.example.restaurantsapp.ui.screens.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.restaurantsapp.R
import com.example.restaurantsapp.ui.component.CircularIndeterminateProgressBar
import com.example.restaurantsapp.ui.component.RestaurantAlertDialog
import com.example.restaurantsapp.ui.component.RestaurantItemList

@Composable
fun RestaurantsListScreen(navController: NavController) {
    val restaurantsViewModel: RestaurantListViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(true) {
        restaurantsViewModel.getNearbyRestaurants()
    }

    restaurantsViewModel.restaurantItems.value.let {
        restaurantsViewModel.error.value?.let { error ->
            RestaurantAlertDialog(
                dialogTitle = context.getString(R.string.restaurant_name),
                dialogText = error,
                icon = Icons.Default.Info
            ) {
                restaurantsViewModel.getNearbyRestaurants()
            }
        }
        restaurantsViewModel.isLoading.value.let { isLoading ->
            CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.4f)
        }
        it?.let {
            RestaurantItemList(
                navController = navController,
                restaurantItemList = it
            )
        }
    }
}