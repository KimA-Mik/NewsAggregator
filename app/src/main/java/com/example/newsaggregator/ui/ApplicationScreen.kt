package com.example.newsaggregator.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.ui.navigation.NavigationHost
import com.example.newsaggregator.ui.util.LocalNavController

@Composable
fun ApplicationScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavigationHost(navController)
    }
}
