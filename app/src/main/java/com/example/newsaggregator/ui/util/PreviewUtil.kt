package com.example.newsaggregator.ui.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.ui.theme.NewsAggregatorTheme

@Composable
fun NAPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) = NewsAggregatorTheme(
    darkTheme = darkTheme,
    dynamicColor = dynamicColor
) {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController()
    ) {
        content()
    }
}