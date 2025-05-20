package com.example.newsaggregator.data.rss.datasource

import com.example.newsaggregator.data.rss.remote.RssFeed
import javax.inject.Inject

class RetrofitRssDataSource @Inject constructor(
    private val guardianRss: RssFeed
) : RemoteRssDataSource {
    override suspend fun getGuardianRss() = guardianRss.getRss()
}