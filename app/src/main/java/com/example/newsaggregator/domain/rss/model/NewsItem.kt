package com.example.newsaggregator.domain.rss.model

data class NewsItem(
    val guid: String,
    val link: String,
    val title: String,
    val description: String,
    val pubDate: Long?,
    val categories: List<String>,
    val imageUrl: String?
)
