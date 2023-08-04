package com.example.jetpack_compose.Darktheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Switch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import  com.example.jetpack_compose.Darktheme.ThemeViewModel

@Composable
fun ThemeSwitch (){
    val themeViewModel:ThemeViewModel= hiltViewModel()
    val themeState by themeViewModel.themeState.collectAsState()

    Switch(
        modifier = Modifier.semantics { contentDescription = "Theme Switch" },
        checked = themeState.isDarkMode,
        onCheckedChange = { themeViewModel.toggleTheme() }
    )

}