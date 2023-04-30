package com.example.taskit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskit.ui.viewmodel.home.HomeViewModel
import com.example.taskit.ui.view.login.LoginViewModel
import com.example.taskit.ui.theme.TaskitTheme
import com.example.taskit.ui.view.login.LoginScreen
import com.example.taskit.ui.viewmodel.home.OfferViewModel
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskitTheme {
                val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
                val profileViewModel = viewModel(modelClass = ProfileViewModel::class.java)
                val offerViewModel = viewModel(modelClass = OfferViewModel::class.java)
                // A surface container using the 'background' color from the theme
                //test
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(loginViewModel = loginViewModel,profileViewModel=profileViewModel, offerViewModel = OfferViewModel())
                }
            }
        }
    }
}


