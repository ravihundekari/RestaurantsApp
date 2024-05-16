package com.example.restaurantsapp.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RestaurantAlertDialog(
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Alert Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()

                }
            ) {
                Text("Retry")
            }
        }
    )
}