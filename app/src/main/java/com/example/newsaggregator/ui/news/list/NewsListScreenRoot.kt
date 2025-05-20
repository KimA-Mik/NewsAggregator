package com.example.newsaggregator.ui.news.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NewsListScreenRoot(modifier: Modifier = Modifier) {
    val viewModel: NewsListScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    NewsListScreen(state = state)
}

@Composable
fun NewsListScreen(
    state: NewsListScreenState,
    modifier: Modifier = Modifier
) {

    Scaffold(modifier = modifier) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.rssFeed.items) {
                Text(it.title)
            }
        }
    }

}