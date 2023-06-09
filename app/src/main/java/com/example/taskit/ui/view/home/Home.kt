package com.example.taskit.ui.view.home

import OfferListScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskit.ui.theme.TaskitTheme
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskit.ui.view.login.LoginViewModel
import com.example.taskit.ui.view.navigation.HomeNavGraph
import com.example.taskit.ui.view.navigation.MyBottomNavigationBar
import com.example.taskit.ui.viewmodel.home.OfferViewModel
import com.example.taskit.ui.viewmodel.navigation.TabItemViewModel
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    tabItemViewmodel : TabItemViewModel = viewModel(),
    profileViewModel: ProfileViewModel,
    offerViewModel: OfferViewModel,
    navController: NavHostController = rememberNavController()
){
    Scaffold(
        bottomBar = { MyBottomNavigationBar(tabItemViewmodel.items,navController ) }
    ) {
        HomeNavGraph(navController,profileViewModel,offerViewModel)
    }
}

@Composable
fun MainTopBar(title: String, navController: NavController){
    TopAppBar(
        title = { Text(
            text="Offers" ,
            fontSize = 25.sp ,
            fontWeight= FontWeight.Bold,
            color= Color.White)

        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate("info")
                }
            ){
                Icon(Icons.Filled.Info, contentDescription = "Info")
            }
        }
    )
}

@Composable
fun TwoButtonRow(navController: NavController){
    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate("Offers") },
                modifier = Modifier.padding(16.dp).size(width = 140.dp, height = 40.dp)
                ,
            ) {
                Text("My Offers")
            }
            Button(
                onClick = { navController.navigate("NewOffer") },
                modifier = Modifier.padding(16.dp).size(width = 140.dp, height = 40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500)),
            ) {
                Text("Add an offer")
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController,offerViewModel: OfferViewModel) {
    Scaffold(
        topBar = { MainTopBar(title = "Offers", navController = navController ) },
        content = {
            Column {
                TwoButtonRow(navController)
                OfferListScreen(offerViewModel = offerViewModel)
            }
        },
    )
}

@Preview
@Composable
fun PrevHomeScreen() {
    TaskitTheme() {
    }
}
