package com.example.jetpack_compose.GoogleMap.Navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpack_compose.GoogleMap.Screens.CurrentLoc
import com.example.jetpack_compose.GoogleMap.SearchLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavGraph(navHostController: NavHostController,locationPermissionState:MultiplePermissionsState,activity: Activity) {
    NavHost(navController = navHostController, startDestination = Screen.CurrentLocation.route ){
        composable(route=Screen.CurrentLocation.route){
            CurrentLoc(navHostController = navHostController, locationPermissionState =locationPermissionState, activity =activity)
        }
        composable(route=Screen.SearchLocation.route){
            SearchLocation(navHostController = navHostController, locationPermissionState =locationPermissionState, activity =activity)
        }
    }
}