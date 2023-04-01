package com.example.taskit.ui.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskit.ui.theme.TaskitTheme
import com.example.taskit.ui.viewmodel.home.HomeViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskit.ui.view.infos.InfoScreen
import com.example.taskit.ui.view.navigation.BottomNavigationBar
import com.example.taskit.ui.view.settings.SettingsScreen

@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    navToLoginPage: () -> Unit,
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ){
        composable(route="Home"){
            MainScreen(navController)
        }
        composable(route = "info"){
            InfoScreen(navController = navController)
        }
        composable(route="Settings"){
            SettingsScreen(navController)
        }
    }
}

@Composable
fun MainTopBar(title: String, navController: NavController){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(
                onClick = {
                    expanded = !expanded
                }
            ){
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }){
                DropdownMenuItem(onClick = { navController.navigate("info") }) {
                    Text("Info")
                }
                DropdownMenuItem(onClick = { navController.navigate("settings") }) {
                    Text("Settings")
                }
            }
        }
    )
}

@Composable
fun ScreenTopBar(title: String, navController: NavController){
    TopAppBar (
        title={ Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp()}){
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController){
    Scaffold(
        topBar = { MainTopBar(title = "Taskit", navController = navController ) },
        content = {
            Column {
                TwoButtonRow();
                DropdownMenu()

            }
        },
        bottomBar = { BottomNavigationBar() }
    )
}

@Composable
fun TwoButtonRow(){
    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Offers")
            }
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500)),
            ) {
                Text("Create an offer")
            }
        }
    }
}

@Preview
@Composable
fun PrevHomeScreen() {
    TaskitTheme() {
        Home(
            homeViewModel = null,
        ) {
        }
    }
}

