package com.example.restaurantsapp.navigation

sealed class Screen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = ""
) {
    data object RestaurantsList : Screen(route = "restaurants_screen")
    data object RestaurantDetails : Screen(
        route = "restaurants_details_screen",
        objectName = "restaurantItem",
        objectPath = "/{restaurantItem}"
    )
}