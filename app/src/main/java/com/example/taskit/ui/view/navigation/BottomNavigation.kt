package com.example.taskit.ui.view.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigationBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /* ... */ },
        ) {
            Icon(
                Icons.Filled.Send,
                contentDescription = "Chat",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Button(
            onClick = { /* ... */ },
        ) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "Account",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Button(
            onClick = { /* ... */ },
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
    }
}