package com.example.taskit.ui.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskit.ui.theme.TaskitTheme
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskit.ui.view.navigation.HomeNavGraph
import com.example.taskit.ui.view.navigation.MyBottomNavigationBar
import com.example.taskit.ui.viewmodel.navigation.TabItem
import com.example.taskit.ui.viewmodel.navigation.TabItemViewModel
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
     tabItemViewmodel : TabItemViewModel = viewModel(),
     profileViewModel: ProfileViewModel,
     navController: NavHostController = rememberNavController()
){
    Scaffold(
        bottomBar = { MyBottomNavigationBar(tabItemViewmodel.items,navController ) }
    ) {
        HomeNavGraph(navController,profileViewModel)
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
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        topBar = { MainTopBar(title = "Taskit", navController = navController ) },
        content = {
            Column {
                TwoButtonRow()
                DropdownMenu()
            }
        },
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
    }
}
