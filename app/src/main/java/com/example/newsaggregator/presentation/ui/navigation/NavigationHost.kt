package com.example.newsaggregator.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.newsaggregator.presentation.ui.news.list.NewsListScreenRoot
import com.example.newsaggregator.presentation.ui.news.view.NewsViewScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SimpleGraph
    ) {
        simpleGraph()
    }
}

fun NavGraphBuilder.simpleGraph() = navigation<SimpleGraph>(SimpleGraph.Root) {
    composable<SimpleGraph.Root> {
        NewsListScreenRoot()
    }

    composable<SimpleGraph.NewsDestination> {
        val route: SimpleGraph.NewsDestination = it.toRoute()
        NewsViewScreenRoot(title = route.title, url = route.url)
    }
}


@Serializable
object SimpleGraph {
    @Serializable
    data object Root

    @Serializable
    data class NewsDestination(val title: String, val url: String)
}