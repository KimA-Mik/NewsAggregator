package com.example.newsaggregator.presentation.ui.news.list

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import com.example.newsaggregator.domain.rss.usecase.GetLatestRssFeedUseCase
import com.example.newsaggregator.domain.rss.usecase.RefreshFeedUseCase
import com.example.newsaggregator.presentation.ui.news.list.event.NewsListUiEvent
import com.example.newsaggregator.presentation.ui.news.list.event.NewsListUserEvent
import com.example.newsaggregator.presentation.ui.news.list.model.DisplayCategory
import com.example.newsaggregator.presentation.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class NewsListScreenViewModel @Inject constructor(
    private val loadRssFeed: GetLatestRssFeedUseCase,
    private val refreshFeed: RefreshFeedUseCase
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) { launchCollection() }
    }

    private val rawFeed = MutableStateFlow(RssFeedItem.default())

    private val loading = MutableStateFlow(false)
    private val selectedCategories = MutableStateFlow(emptySet<String>())
    private val controlSheet = MutableStateFlow(false)
    private val _uiEvent = MutableStateFlow(UiEvent<NewsListUiEvent>(null))
    val uiEvent = _uiEvent.asStateFlow()

    private val analysedFeed = rawFeed.map { feed ->
        val set = mutableSetOf<String>()
        feed.items.forEach { set.addAll(it.categories) }
        val categories = set.toMutableList().sorted()
        selectedCategories.value = selectedCategories.value.intersect(set)
        Pair(feed.items, categories)
    }

    private val displayCategories =
        combine(analysedFeed, selectedCategories) { analysedFeed, selectedCategories ->
            analysedFeed.component2().map {
                DisplayCategory(value = it, selected = selectedCategories.contains(it))
            }
        }

    private val filteredNews =
        combine(analysedFeed, selectedCategories) { analysedFeed, selectedCategories ->
            analysedFeed.component1().filter { newsItem ->
                selectedCategories.all { newsItem.categories.contains(it) }
            }
        }

    private suspend fun launchCollection() = loadRssFeed().collect { res ->
        when (res) {
            is GetLatestRssFeedUseCase.Result.Success -> rawFeed.update { res.feed }
            GetLatestRssFeedUseCase.Result.Loading -> loading.update { true }
            GetLatestRssFeedUseCase.Result.LoadingComplete -> loading.update { false }
            GetLatestRssFeedUseCase.Result.UnableToLoad -> loading.update { false }
            GetLatestRssFeedUseCase.Result.NoInternetConnection -> _uiEvent.update {
                UiEvent(NewsListUiEvent.NoInternetConnection)
            }

            GetLatestRssFeedUseCase.Result.UnknownError -> _uiEvent.update {
                UiEvent(NewsListUiEvent.UnknownLoadingError)
            }
        }
    }

    val state = combine(
        filteredNews,
        loading,
        displayCategories,
        controlSheet
    ) { analysedFeed, loading, categories, controlSheet ->
        NewsListScreenState(
            displayNews = analysedFeed,
            isLoading = loading,
            categories = categories,
            controlSheet = controlSheet
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), NewsListScreenState())

    fun onEvent(event: NewsListUserEvent) {
        when (event) {
            NewsListUserEvent.RefreshFeed -> onRefreshFeed()
            is NewsListUserEvent.OpenNews -> onOpenNews(event.guid)
            NewsListUserEvent.OpenControlSheet -> onOpenControlSheet()
            NewsListUserEvent.DismissControlSheet -> onDismissControlSheet()
            is NewsListUserEvent.SelectCategory -> onSelectCategory(event.category)
        }
    }

    private fun onOpenNews(guid: String) {
        val news = rawFeed.value.items.find { it.guid == guid } ?: return
        _uiEvent.value = UiEvent(NewsListUiEvent.OpenNews(news.title, news.link))
    }

    private fun onRefreshFeed() = viewModelScope.launch {
        refreshFeed().collect { state ->
            when (state) {
                RefreshFeedUseCase.Result.Loading -> loading.update { true }
                RefreshFeedUseCase.Result.Success -> loading.update { false }
                RefreshFeedUseCase.Result.NoInternet -> _uiEvent.update {
                    loading.update { false }
                    UiEvent(NewsListUiEvent.NoInternetConnection)
                }

                RefreshFeedUseCase.Result.UnknownError -> _uiEvent.update {
                    loading.update { false }
                    UiEvent(NewsListUiEvent.UnknownLoadingError)
                }
            }
        }
    }

    private fun onOpenControlSheet() {
        controlSheet.value = true
    }

    private fun onDismissControlSheet() {
        controlSheet.value = false
    }

    private fun onSelectCategory(category: String) {
        val set = selectedCategories.value.toMutableSet()
        if (set.contains(category)) {
            set.remove(category)
        } else {
            set.add(category)
        }
        selectedCategories.value = set
    }
}