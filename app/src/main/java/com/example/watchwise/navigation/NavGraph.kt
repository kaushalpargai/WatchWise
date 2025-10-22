package com.example.watchwise.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.watchwise.ui.details.MediaDetailsScreen
import com.example.watchwise.ui.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{mediaId}") {
        fun createRoute(mediaId: String) = "details/$mediaId"
    }
}

@Composable
fun WatchWiseNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMediaClick = { mediaId ->
                    navController.navigate(Screen.Details.createRoute(mediaId))
                }
            )
        }
        
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("mediaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mediaId = backStackEntry.arguments?.getString("mediaId") ?: return@composable
            MediaDetailsScreen(
                mediaId = mediaId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
