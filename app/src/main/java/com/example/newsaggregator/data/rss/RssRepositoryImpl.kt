package com.example.newsaggregator.data.rss

import com.example.newsaggregator.data.rss.dataSource.RemoteRssDataSource
import com.example.newsaggregator.domain.rss.RssRepository
import com.example.newsaggregator.domain.rss.model.NewsItem
import javax.inject.Inject

class RssRepositoryImpl @Inject constructor(
    private val remoteRssDataSource: RemoteRssDataSource
) : RssRepository {
    override suspend fun getLatestFeed(): List<NewsItem> {
        TODO("Not yet implemented")
    }
}