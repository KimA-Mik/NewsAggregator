package com.example.newsaggregator.domain.rss.model

data class NewsItem(
    val guid: String,
    val title: String,
    val description: String,
    val pubDate: Long,
    val categories: List<CategoryItem>,
    val imageTitle: String?,
    val imageUrl: String?
)
