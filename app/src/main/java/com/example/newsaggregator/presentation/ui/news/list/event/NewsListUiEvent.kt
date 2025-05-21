package com.example.newsaggregator.presentation.ui.news.list.event

sealed interface NewsListUiEvent {
    data object NoInternetConnection : NewsListUiEvent
    data object UnknownLoadingError : NewsListUiEvent
    data class OpenNews(val title: String, val url: String) : NewsListUiEvent
}