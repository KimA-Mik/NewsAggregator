package com.example.newsaggregator.data.rss.datasource

import com.example.newsaggregator.data.rss.local.Database
import com.example.newsaggregator.data.rss.local.entities.GuardianFeedWithNews
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRssCacheDataSource @Inject constructor(database: Database) : LocalRssCacheDataSource {
    private val dao = database.guardianRssDao()
    override suspend fun insertFeed(feed: GuardianFeedWithNews) {
        dao.insertFeed(feed.feed, feed.news)
    }

    override fun getLatestFeed(): Flow<GuardianFeedWithNews?> {
        return dao.subscribeToLatestFeed()
    }

    override suspend fun getAllCachedFeeds(): List<Long> {
        return dao.getAllFeeds().map { it.idTimestamp }
    }

    override suspend fun deleteFeedsTransaction(feeds: List<Long>) {
        dao.deleteFeedsTransaction(feeds)
    }
}