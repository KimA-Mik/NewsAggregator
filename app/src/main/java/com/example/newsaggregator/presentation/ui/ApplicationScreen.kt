package com.example.newsaggregator.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.presentation.ui.navigation.NavigationHost
import com.example.newsaggregator.presentation.ui.util.LocalNavController

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavigationHost(navController)
    }
}
