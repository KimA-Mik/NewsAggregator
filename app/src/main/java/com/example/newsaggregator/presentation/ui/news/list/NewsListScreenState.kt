package com.example.newsaggregator.presentation.ui.news.list

import androidx.compose.runtime.Immutable
import com.example.newsaggregator.domain.rss.model.NewsItem
import com.example.newsaggregator.presentation.ui.news.list.model.DisplayCategory

@Immutable
data class NewsListScreenState(
    val displayNews: List<NewsItem> = emptyList(),
    val isLoading: Boolean = true,
    val categories: List<DisplayCategory> = emptyList(),
    val controlSheet: Boolean = false
)
