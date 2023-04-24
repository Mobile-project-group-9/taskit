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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskit.ui.view.navigation.HomeNavGraph
import com.example.taskit.ui.view.navigation.MyBottomNavigationBar
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

@Composable
fun TwoButtonRow(navController: NavController){
    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate("Offers") },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Offers")
            }
            Button(
                onClick = { navController.navigate("NewOffer") },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500)),
            ) {
                Text("Create an offer")
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = { MainTopBar(title = "Taskit", navController = navController ) },
        content = {
            Column {
                TwoButtonRow(navController)
                Text(
                    text = "Offers",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                DropdownMenu()
                Spacer(modifier = Modifier.height(30.dp))
                OfferListScreen()
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownMenu() {
    val listItems = arrayOf("Household", "Babysitting", "Gardening", "Other")
    val contextForToast = LocalContext.current.applicationContext

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // remember the selected item
    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // text field
            TextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Label") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // this is a column scope
                // all the items are added vertically
                listItems.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(onClick = {
                        selectedItem = selectedOption
                        Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT).show()
                        expanded = false
                    }) {
                        Text(text = selectedOption)
                    }
                }

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
