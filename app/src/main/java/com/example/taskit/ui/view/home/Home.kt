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
                DropdownList()
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

@Composable
fun DropdownList(){
    val items = listOf("Option 1", "Option 2", "Option 3")
    val (expanded, setExpanded) = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Select an option:",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colors.onSurface, shape = RoundedCornerShape(4.dp))
                .clickable(onClick = { setExpanded(!expanded) })
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = items[selectedIndex.value],
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex.value = index
                        setExpanded(false)
                    }
                ) {
                    Text(text = text)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /* ... */ },
        ) {
            Icon(
                Icons.Filled.Send,
                contentDescription = "Chat",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Button(
            onClick = { /* ... */ },
        ) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "Account",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        Button(
            onClick = { /* ... */ },
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
    }
}

// ------ ADD THE SIGN OUT FUNCTION IN THE PROFILE PAGE -------

/*
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    navToLoginPage: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        homeViewModel?.signOut()
                        navToLoginPage.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(text = "Home")
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
        }
    }
}

 */

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


