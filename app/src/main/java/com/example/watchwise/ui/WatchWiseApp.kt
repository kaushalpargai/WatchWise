package com.example.watchwise.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.watchwise.navigation.WatchWiseNavHost

@Composable
fun WatchWiseApp() {
    val navController = rememberNavController()
    WatchWiseNavHost(navController = navController)
}
