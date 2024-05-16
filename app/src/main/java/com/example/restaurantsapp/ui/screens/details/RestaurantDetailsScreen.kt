package com.example.restaurantsapp.ui.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.restaurantsapp.R
import com.example.restaurantsapp.data.model.RestaurantItem
import com.example.restaurantsapp.ui.component.CircularIndeterminateProgressBar
import com.example.restaurantsapp.ui.component.RestaurantAlertDialog
import com.example.restaurantsapp.ui.component.RestaurantItemView

@Composable
fun RestaurantDetailsScreen(restaurantItem: RestaurantItem) {
    val restaurantDetailsViewModel = hiltViewModel<RestaurantDetailsViewModel>()
    val restaurantDetail = restaurantDetailsViewModel.restaurantDetail
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        restaurantDetailsViewModel.getRestaurantDetails(restaurantItem.fsqId)
    }

    restaurantDetailsViewModel.error.value?.let { error ->
        RestaurantAlertDialog(
            dialogTitle = context.getString(R.string.restaurant_details),
            dialogText = error,
            icon = Icons.Default.Info
        ) {
            restaurantDetailsViewModel.getRestaurantDetails(restaurantItem.fsqId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        restaurantDetailsViewModel.isLoading.value.let { isLoading ->
            CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.4f)
        }

        restaurantDetail.value?.let { details ->
            RestaurantItemView(
                restaurantItem.restaurantsPhotos,
                restaurantItem.name,
                restaurantItem.closedBucket
            ) {}
            Text(
                text = "Address: ${
                    if (details.location.formattedAddress.isNullOrEmpty()) {
                        "Not Available"
                    } else details.location.formattedAddress
                }",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .align(Alignment.Start),
                style = MaterialTheme.typography.bodyLarge
            )

            details.geocodes.main?.let {
                FilledTonalButton(onClick = {
                    val mapIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=${details.geocodes.main.latitude},${details.geocodes.main.longitude}")
                    )
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                }) {
                    Text(text = "Navigate")
                }
            }
        }
    }
}