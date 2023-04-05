package com.example.taskit.ui.viewmodel.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel


class TabItemViewModel : ViewModel() {
    val items = listOf(
        TabItem("Home", Icons.Filled.Home,"Home"),
        TabItem("Chat",Icons.Filled.AccountBox,"Chat"),
        TabItem("Favourites",Icons.Filled.Favorite,"Favourites"),
        TabItem("Profile",Icons.Filled.AccountCircle,"Profile"),

    )
}