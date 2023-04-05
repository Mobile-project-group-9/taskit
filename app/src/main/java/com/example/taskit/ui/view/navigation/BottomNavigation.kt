package com.example.taskit.ui.view.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskit.ui.view.home.MainScreen
import com.example.taskit.ui.view.profile.ProfileScreen
import com.example.taskit.ui.viewmodel.navigation.TabItem
import com.example.taskit.ui.viewmodel.navigation.TabItemViewModel

/*
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

<<<<<<< Updated upstream


=======
 */
/*
@Composable
fun BottomNavigationBar(navController: NavController){
    BottomAppBar(){
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
}

 */

@Composable
fun MyBottomNavigationBar(items: List<TabItem>, navController: NavController){
    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation() {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem == index
                    navController.navigate(item.route)
                },
                icon ={ Icon(item.icon, contentDescription = null)},
                label = {Text(item.label)}
            )

        }
    }
}

@Composable
fun NavigationHost(tabItemViewModel: TabItemViewModel= viewModel()){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Home"
    ){
        composable(route ="Home"){
            MainScreen(navController,tabItemViewModel.items)
        }
        composable(route="Chat"){

        }
        composable(route="Favourites"){

        }
        composable(route = "Profile"){
            ProfileScreen(navController,tabItemViewModel.items)
        }
    }
}