package com.example.newsaggregator.domain.rss

import com.example.newsaggregator.domain.rss.model.NewsItem

interface RssRepository {
    suspend fun getLatestFeed(): List<NewsItem>
}