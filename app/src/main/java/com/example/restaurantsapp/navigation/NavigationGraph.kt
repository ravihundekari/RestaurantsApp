package com.example.restaurantsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.restaurantsapp.R
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.domain.model.RestaurantItemArgType
import com.example.restaurantsapp.presentation.screens.details.RestaurantDetailsScreen
import com.example.restaurantsapp.presentation.screens.list.RestaurantsListScreen
import com.google.gson.Gson

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier
) {
    NavHost(navController, startDestination = Screen.RestaurantsList.route, modifier) {
        composable(Screen.RestaurantsList.route) {
            RestaurantsListScreen(
                navController = navController
            )
        }
        composable(
            Screen.RestaurantDetails.route.plus(Screen.RestaurantDetails.objectPath),
            arguments = listOf(navArgument(Screen.RestaurantDetails.objectName) {
                type = RestaurantItemArgType()
            })
        ) {
            label = stringResource(R.string.restaurant_details)
            val restaurant =
                it.arguments?.getString(Screen.RestaurantDetails.objectName)?.let { res ->
                    Gson().fromJson(res, RestaurantItem::class.java)
                }
            restaurant?.let { RestaurantDetailsScreen(restaurant) }
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.RestaurantsList.route -> stringResource(id = R.string.restaurant_name)
        Screen.RestaurantDetails.route -> stringResource(id = R.string.restaurant_details)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}