package com.example.taskit.ui.view.infos

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.taskit.ui.view.home.ScreenTopBar
import com.example.taskit.ui.viewmodel.navigation.TabItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InfoScreen(navController: NavController){
    Scaffold(
        topBar = { ScreenTopBar("Info", navController)},
        content = { Text(text="Content for Info Screen") },
    )
}
