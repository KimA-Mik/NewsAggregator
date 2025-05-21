package com.example.newsaggregator.ui.news.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsaggregator.R
import com.example.newsaggregator.ui.navigation.SimpleGraph
import com.example.newsaggregator.ui.news.list.event.NewsListUiEvent
import com.example.newsaggregator.ui.news.list.event.NewsListUserEvent
import com.example.newsaggregator.ui.util.LocalNavController
import com.example.newsaggregator.ui.util.LocalSnackbarHostState
import com.example.newsaggregator.ui.util.UiEvent
import kotlinx.coroutines.launch

@Composable
fun NewsListScreenRoot(modifier: Modifier = Modifier) {
    val viewModel: NewsListScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()
    val onEvent = remember<(NewsListUserEvent) -> Unit> {
        { viewModel.onEvent(it) }
    }
    NewsListScreen(
        state = state,
        uiEvent = uiEvent,
        onEvent = onEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    state: NewsListScreenState,
    uiEvent: UiEvent<NewsListUiEvent>,
    onEvent: (NewsListUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val navController = LocalNavController.current

    LaunchedEffect(uiEvent) {
        uiEvent.consume { event ->
            when (event) {
                NewsListUiEvent.NoInternetConnection -> coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = context.getString(R.string.snackbar_message_no_internet_connection))
                }

                NewsListUiEvent.UnknownLoadingError -> coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = context.getString(R.string.snackbar_message_unknown_error))
                }

                is NewsListUiEvent.OpenNews -> {
                    navController.navigate(SimpleGraph.NewsDestination(event.title, event.url))
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_news)) },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        NewsListScreenContent(
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .padding(padding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewsListScreenContent(
    state: NewsListScreenState,
    onEvent: (NewsListUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { onEvent(NewsListUserEvent.RefreshFeed) },
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = state.rssFeed.items,
                key = { it.guid }
            ) {
                Text(
                    it.title,
                    modifier = Modifier.clickable { onEvent(NewsListUserEvent.OpenNews(it.guid)) })
            }
        }
    }
}