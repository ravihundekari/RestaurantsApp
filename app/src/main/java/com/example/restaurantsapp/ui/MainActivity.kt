package com.example.restaurantsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.restaurantsapp.navigation.Navigation
import com.example.restaurantsapp.navigation.Screen
import com.example.restaurantsapp.navigation.currentRoute
import com.example.restaurantsapp.navigation.navigationTitle
import com.example.restaurantsapp.ui.component.appbar.AppBarWithArrow
import com.example.restaurantsapp.ui.component.appbar.HomeAppBar
import com.example.restaurantsapp.ui.theme.RestaurantsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantsAppTheme {
                RestaurantsApplication()
            }
        }
    }
}

@Composable
fun RestaurantsApplication() {
    val navController = rememberNavController()
    Scaffold(topBar = {
        when (currentRoute(navController)) {
            Screen.RestaurantsList.route -> {
                HomeAppBar(title = navigationTitle(navController = navController))
            }

            Screen.RestaurantDetails.route -> {
                AppBarWithArrow(navigationTitle(navController)) {
                    navController.popBackStack()
                }
            }
        }
    }) {
        Navigation(navController = navController, modifier = Modifier.padding(it))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RestaurantsAppTheme {
        RestaurantsApplication()
    }
}