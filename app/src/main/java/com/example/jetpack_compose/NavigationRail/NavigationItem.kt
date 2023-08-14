package com.example.jetpack_compose.NavigationRail

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title:String,
    val unselectedIcon:ImageVector,
    val selectedIcon:ImageVector,
    val badgeCount:Int?=null,
    val hasNews:Boolean
)
