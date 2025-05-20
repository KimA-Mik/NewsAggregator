package com.example.newsaggregator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.newsaggregator.ui.news.list.NewsListScreenRoot

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SimpleGraph.ROOT
    ) {
        simpleGraph()
    }
}

fun NavGraphBuilder.simpleGraph() {
    navigation(startDestination = SimpleGraph.ROOT, route = SimpleGraph.ROUTE) {
        composable(SimpleGraph.ROOT) {
            NewsListScreenRoot()
        }
    }
}

object SimpleGraph {
    const val ROUTE = "simple_graph"
    const val ROOT = "news_feed"
}