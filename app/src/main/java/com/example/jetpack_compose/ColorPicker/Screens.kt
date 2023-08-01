package com.example.jetpack_compose.ColorPicker

sealed class Screens(val route: String) {
    object NavScreen: Screens(route = "nav_screen")
}