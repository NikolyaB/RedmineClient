package com.example.redmineclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.navigator.ScreenRoute
import com.example.redmineclient.presentation.ui.mainNavigation.view.MainNavigationScreen
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    private val startDestination: ScreenRoute = ScreenRoute.Authentication
    private val rootNavigation: Navigator by inject { parametersOf(startDestination) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            rootNavigation.init(navController)
            MaterialTheme(
                shapes = MaterialTheme.shapes,
            ) {
                MainNavigationScreen(startDestination, navController)
            }
        }
    }
}