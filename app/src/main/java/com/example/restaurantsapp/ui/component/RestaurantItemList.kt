package com.example.restaurantsapp.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.restaurantsapp.data.model.RestaurantItem
import com.example.restaurantsapp.navigation.Screen

@Composable
fun RestaurantItemList(
    navController: NavController,
    restaurantItemList: List<RestaurantItem>,
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        items(restaurantItemList) { item ->
            RestaurantItemView(
                item.restaurantsPhotos,
                item.name,
                item.closedBucket
            ) {
                navController.navigate(
                    Screen.RestaurantDetails.route.plus("/${item}")
                )
            }
        }
    }
}