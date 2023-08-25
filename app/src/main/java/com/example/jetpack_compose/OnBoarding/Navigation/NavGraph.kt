package com.example.jetpack_compose.OnBoarding.Navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpack_compose.GoogleMap.Screens.CurrentLoc
import com.example.jetpack_compose.GoogleMap.SearchLocation
import com.example.jetpack_compose.OnBoarding.Screens.HomeScreen
import com.example.jetpack_compose.OnBoarding.Screens.WelcomeScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@Composable
fun NavGraph(navHostController: NavHostController,start:String) {
    NavHost(navController = navHostController, startDestination = start){
        composable(route=Screen.Welcome.route){
            WelcomeScreen(navController = navHostController)
        }
        composable(route=Screen.Home.route){
            HomeScreen()
        }
    }
}