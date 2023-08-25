package com.example.jetpack_compose.GoogleMap.Navigation

sealed class  Screen(val route:String) {
    object CurrentLocation:Screen("Current_Loc")
    object SearchLocation:Screen("Search_Loc")
}