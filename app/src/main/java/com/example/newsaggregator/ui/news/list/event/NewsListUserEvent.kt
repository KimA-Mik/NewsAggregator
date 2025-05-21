package com.example.newsaggregator.ui.news.list.event

sealed interface NewsListUserEvent {
    data object RefreshFeed : NewsListUserEvent
}