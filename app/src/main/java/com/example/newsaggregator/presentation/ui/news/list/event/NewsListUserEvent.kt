package com.example.newsaggregator.presentation.ui.news.list.event

sealed interface NewsListUserEvent {
    data object RefreshFeed : NewsListUserEvent
    data class OpenNews(val guid: String) : NewsListUserEvent
    data object OpenControlSheet : NewsListUserEvent
    data object DismissControlSheet : NewsListUserEvent
    data class SelectCategory(val category: String) : NewsListUserEvent
}