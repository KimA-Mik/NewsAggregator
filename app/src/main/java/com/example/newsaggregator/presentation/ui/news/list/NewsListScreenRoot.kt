package com.example.newsaggregator.presentation.ui.news.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsaggregator.R
import com.example.newsaggregator.presentation.ui.navigation.SimpleGraph
import com.example.newsaggregator.presentation.ui.news.list.components.ControlSheet
import com.example.newsaggregator.presentation.ui.news.list.components.NewsListItem
import com.example.newsaggregator.presentation.ui.news.list.event.NewsListUiEvent
import com.example.newsaggregator.presentation.ui.news.list.event.NewsListUserEvent
import com.example.newsaggregator.presentation.ui.news.list.model.DisplayCategory
import com.example.newsaggregator.presentation.ui.util.LocalNavController
import com.example.newsaggregator.presentation.ui.util.LocalSnackbarHostState
import com.example.newsaggregator.presentation.ui.util.UiEvent
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

    when {
        state.controlSheet -> ControlSheet(
            categories = state.categories,
            onDismissRequest = { onEvent(NewsListUserEvent.DismissControlSheet) },
            onSelectChip = { onEvent(NewsListUserEvent.SelectCategory(it)) },
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_news)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { onEvent(NewsListUserEvent.OpenControlSheet) }) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = null)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        NewsListScreenContent(
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .padding(padding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
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
        val bodyModifier = Modifier.fillMaxSize()
        if (state.displayNews.isEmpty() && !state.isLoading) {
            EmptyListLabel(
                categories = state.categories,
                modifier = bodyModifier
            )
        } else {
            NewsList(state = state, onEvent = onEvent, modifier = bodyModifier)
        }
    }
}

@Composable
private fun NewsList(
    state: NewsListScreenState,
    onEvent: (NewsListUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = state.displayNews,
            key = { it.guid }
        ) {
            val click =
                remember { Modifier.clickable { onEvent(NewsListUserEvent.OpenNews(it.guid)) } }
            NewsListItem(
                item = it,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .then(click)
            )
        }
    }
}

@Composable
private fun EmptyListLabel(
    categories: List<DisplayCategory>,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    val anyCardsSelected = categories.fastAny { it.selected }
    Text(
        text = if (anyCardsSelected) {
            stringResource(R.string.label_unable_to_match_categories)
        } else {
            stringResource(R.string.label_unable_to_load)
        },
        style = MaterialTheme.typography.labelLarge
    )
}
