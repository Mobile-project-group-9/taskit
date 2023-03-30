package com.example.taskit.ui.view.settings

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.taskit.ui.view.home.ScreenTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController){
    Scaffold(
        topBar = { ScreenTopBar(title = "Settings", navController = navController)},
        content = { Text(text = "Content for Settings screen") },
    )
}
