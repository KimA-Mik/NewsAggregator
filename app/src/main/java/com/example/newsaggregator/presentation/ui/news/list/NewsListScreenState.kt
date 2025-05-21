package com.example.newsaggregator.presentation.ui.news.list

import com.example.newsaggregator.domain.rss.model.RssFeedItem

data class NewsListScreenState(
    val rssFeed: RssFeedItem = RssFeedItem.default(),
    val isLoading: Boolean = false
)
