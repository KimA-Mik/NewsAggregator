package com.example.newsaggregator.domain.rss.model

data class RssFeedItem(
    val idTimestamp: Long,
    val items: List<NewsItem>
)
