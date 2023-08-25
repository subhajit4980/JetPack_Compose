package com.example.jetpack_compose.OnBoarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose.Component
import com.example.jetpack_compose.OnBoarding.Navigation.NavGraph
import com.example.jetpack_compose.OnBoarding.viewModel.SplashViewModel
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class Onboarding : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }

        setContent {
            Jetpack_composeTheme(dynamicColor = true) {
                Surface() {
                    Component.darkmode(LocalContext.current)
                    val screen by splashViewModel.startDestination
                    val navController = rememberNavController()
                    NavGraph(navHostController = navController , start =screen )
                }
            }
        }
    }
}
