package com.example.newsaggregator.presentation.ui.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.presentation.ui.theme.NewsAggregatorTheme

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
        Surface {
            content()
        }
    }
}