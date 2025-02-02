package com.example.restaurantsapp.presentation.screens.details

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.restaurantsapp.R
import com.example.restaurantsapp.domain.model.RestaurantDetail
import com.example.restaurantsapp.domain.model.RestaurantItem
import com.example.restaurantsapp.presentation.component.CircularIndeterminateProgressBar
import com.example.restaurantsapp.presentation.component.RestaurantAlertDialog
import com.example.restaurantsapp.presentation.component.RestaurantItemView
import com.example.restaurantsapp.utils.network.DataState

@Composable
fun RestaurantDetailsScreen(restaurantItem: RestaurantItem) {
    val restaurantDetailsViewModel = hiltViewModel<RestaurantDetailsViewModel>()

    val restaurantDetailState by restaurantDetailsViewModel.restaurantDetail.collectAsStateWithLifecycle()
    var isLoading by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        restaurantDetailsViewModel.getRestaurantDetails(restaurantItem.fsqId)
    }

    when (restaurantDetailState) {
        is DataState.Error -> {
            isLoading = false
            RestaurantAlertDialog(
                dialogTitle = context.getString(R.string.restaurant_details),
                dialogText = (restaurantDetailState as DataState.Error).message.toString(),
                icon = Icons.Default.Info
            ) {
                restaurantDetailsViewModel.getRestaurantDetails(restaurantItem.fsqId)
            }
        }

        DataState.Loading -> {
            CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.4f)
        }

        is DataState.Success -> {
            isLoading = false
            val restaurantDetail =
                (restaurantDetailState as DataState.Success<RestaurantDetail>).data
            Log.d("RestaurantDetailsScreen", "Success $restaurantDetail")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                RestaurantItemView(
                    restaurantItem.restaurantsPhotos,
                    restaurantItem.name,
                    restaurantItem.closedBucket
                ) {}
                Text(
                    text = "Address: ${
                        if (restaurantDetail.location.formattedAddress.isNullOrEmpty()) {
                            "Not Available"
                        } else restaurantDetail.location.formattedAddress
                    }",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.bodyLarge
                )

                restaurantDetail.geocodes.main?.let {
                    FilledTonalButton(onClick = {
                        val mapIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=${restaurantDetail.geocodes.main.latitude},${restaurantDetail.geocodes.main.longitude}")
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

}