package com.example.jetpack_compose.ColorPicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme

class ImageColorPicker : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val paletteViewModel: PaletteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    NavGraph(navController = navController, paletteViewModel = paletteViewModel)
                }
            }
        }
    }

    @Composable
    fun NavGraph(
        navController: NavHostController,
        paletteViewModel: PaletteViewModel
    ) {

        NavHost(navController = navController, startDestination = Screens.NavScreen.route) {
            composable(route = Screens.NavScreen.route) {
                NavScreen(
                    navController = navController,
                    paletteViewModel = paletteViewModel
                )
            }
        }
    }
}

