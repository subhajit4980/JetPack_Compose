package com.example.jetpack_compose.OnBoarding.Navigation

sealed class  Screen(val route:String) {
    object Welcome:Screen("Current_Loc")
    object Home:Screen("Search_Loc")
}