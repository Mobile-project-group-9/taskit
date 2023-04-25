package com.example.taskit

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskit.ui.view.chatBox.ChatScreen
import com.example.taskit.ui.view.home.Home
import com.example.taskit.ui.view.home.MainScreen
import com.example.taskit.ui.viewmodel.home.HomeViewModel
import com.example.taskit.ui.view.login.LoginScreen
import com.example.taskit.ui.view.login.LoginViewModel
import com.example.taskit.ui.view.login.SignUpScreen

import com.example.taskit.ui.view.login.SplashScreen



import com.example.taskit.ui.view.profile.ProfileScreen
import com.example.taskit.ui.viewmodel.navigation.TabItem
import com.example.taskit.ui.viewmodel.navigation.TabItemViewModel
import com.example.taskit.ui.viewmodel.profile.ProfileViewModel



enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home,
    Profile
}

enum class NestedRoutes {
    Main,
    Login,
    Splash
}



@Composable
fun Navigation(
    navController: NavHostController= rememberNavController(),
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel,
    tabItemViewmodel : TabItemViewModel = viewModel()
){
    NavHost(
        navController=navController,
        route="root_graph",
        startDestination = "splash_screen"
    ){
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        authNavGraph(navController = navController, loginViewModel = loginViewModel, profileViewModel = profileViewModel )
        composable(route=NestedRoutes.Main.name){
            Home(tabItemViewmodel,profileViewModel)
        }
    }
}
//fun Navigation(
//    navController: NavHostController= rememberNavController(),
//    loginViewModel: LoginViewModel,
//    profileViewModel: ProfileViewModel,
//    tabItemViewmodel : TabItemViewModel = viewModel()
//){
//    NavHost(
//        navController = navController,
//        startDestination = "splash_screen"
//    ) {
//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
//        authGraph(navController, loginViewModel)
//        homeGraph(
//            navController = navController,
//            homeViewModel
//        )
//    }
//
//
//
//        navController=navController,
//        route="root_graph",
//        startDestination = NestedRoutes.Login.name
//    ){
//        authNavGraph(navController = navController, loginViewModel = loginViewModel, profileViewModel = profileViewModel )
//        composable(route=NestedRoutes.Main.name){
//            Home(tabItemViewmodel,profileViewModel)
//        }
//    }
//}






fun  NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    profileViewModel: ProfileViewModel
){
    navigation(
        route=NestedRoutes.Login.name,
        startDestination = LoginRoutes.SignIn.name,
    ){
        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name)
                    {
                        launchSingleTop = true
                        popUpTo(LoginRoutes.SignIn.name) {
                            inclusive = true
                        }
                    }
                },
                onNavToSignUpPage= {
                    navController.navigate(LoginRoutes.Signup.name) {
                        launchSingleTop = true
                        popUpTo(LoginRoutes.SignIn.name) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = loginViewModel
            )
        }
        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        popUpTo(LoginRoutes.Signup.name) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
    }
}







