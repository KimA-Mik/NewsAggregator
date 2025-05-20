package com.example.newsaggregator.ui.util

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavController = compositionLocalOf<NavController> {
    error("CompositionLocal LocalNavController not presented")
}