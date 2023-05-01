package com.example.taskit.ui.view.infos

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
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
fun InfoScreen(navController: NavController){
    Scaffold(
        topBar = { ScreenTopBar("Info", navController)},
        content = { InfoCard() },
    )
}


@Composable
fun InfoCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight() // added to fill the screen
    ) {
        Box(modifier = Modifier.fillMaxSize()) { // added to make content scrollable
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // added to make content scrollable
            ) {
                Text(
                    text = "Hiring application",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "The application is a service exchange platform that simplifies access to work and promotes local relationships.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "#Functionalities : ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- The application will be exclusive to the finnish population",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- Categories for each type of services (housework, repairs, chores, garden work)",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- Chat so that both sides can communicate.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- Easy automatic contrat maker between the two sides.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "#Two type of actions for one same account : ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- Request for a job ",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- Offer for a job",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "#User experience :",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- The user offering a job will put the offer in a specific category. The requesting user will search through all the categories.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- The user will be able to message another one through a chat do discuss about the details.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "- When both users agree on the work, they can sign a simple electronic contract through the app.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

