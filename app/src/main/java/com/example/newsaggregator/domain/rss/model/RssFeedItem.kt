package com.example.newsaggregator.domain.rss.model

data class RssFeedItem(
    val id: Long,
    val updateDate: Long,
    val items: List<NewsItem>
)
