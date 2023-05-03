package com.example.taskit.ui.view.navigation

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.taskit.HomeRoutes
import com.example.taskit.LoginRoutes
import com.example.taskit.NestedRoutes
import com.example.taskit.ui.view.chatBox.ChatMessagesScreen
import com.example.taskit.ui.view.chatBox.ChatScreen
import com.example.taskit.ui.view.home.Home
import com.example.taskit.ui.view.chatBox.ChatScreen
import com.example.taskit.ui.view.favourites.FavouriteScreen
import com.example.taskit.ui.view.home.MainScreen
import com.example.taskit.ui.view.infos.InfoScreen
import com.example.taskit.ui.view.login.LoginViewModel
import com.example.taskit.ui.view.newOffer.NewOffer
import com.example.taskit.ui.view.profile.*
import com.example.taskit.ui.viewmodel.home.OfferViewModel
import com.example.taskit.ui.viewmodel.navigation.TabItem
import com.example.taskit.ui.viewmodel.navigation.TabItemViewModel
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel

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
fun HomeNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    offerViewModel: OfferViewModel
){
    NavHost(
        navController=navController,
        route=NestedRoutes.Main.name,
        startDestination = HomeRoutes.Home.name
    ){
        composable(route = HomeRoutes.Home.name){
            MainScreen(navController,offerViewModel)
        }
        composable(route="Chat"){
            ChatScreen("user",navController)
        }
        composable(route="Favourites"){
            FavouriteScreen(profileViewModel, offerViewModel)
        }
        composable(HomeRoutes.Profile.name){
            ProfileScreen(navController,profileViewModel,onSignOut = {navController.navigate(NestedRoutes.Login.name)})
        }
        composable(route = "NewOffer"){
            NewOffer(navController)
        }
        composable(route = "Offers"){
            OfferScreen(navController)
        }
        composable(route = "Info"){
            InfoScreen(navController)
        }
        composable(route="Edit"){
            EditScreen(navController,profileViewModel)
        }
        composable("camera"){
            CameraPermissionsView(profileViewModel)
        }
        composable("device"){
            DeviceImage(navController, profileViewModel )
        }
        composable("applications"){
            TaskScreen(profileViewModel)
        }
        composable("message"){
            ChatMessagesScreen("user",navController)
        }
    }
}



