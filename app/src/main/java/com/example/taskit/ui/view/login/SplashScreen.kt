package com.example.taskit.ui.view.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskit.NestedRoutes
import com.example.taskit.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true) {
        delay(4000L)
        navController.navigate(NestedRoutes.Login.name)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.taskit_logo),"",
            modifier = Modifier.width(400.dp).height(400.dp)
            )
    }
}