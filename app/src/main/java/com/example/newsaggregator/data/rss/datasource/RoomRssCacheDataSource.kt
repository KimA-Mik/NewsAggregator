package com.example.newsaggregator.data.rss.datasource

import com.example.newsaggregator.data.rss.local.Database
import com.example.newsaggregator.data.rss.local.entities.GuardianFeedWithNews
import com.example.newsaggregator.data.rss.local.entities.GuardianRssFeedEntity
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

    override suspend fun getAllFeeds(): List<GuardianRssFeedEntity> {
        return dao.getAllFeeds()
    }

    override suspend fun deleteFeedsTransaction(feeds: List<GuardianRssFeedEntity>) {
        dao.deleteFeedsTransaction(feeds.map { it.idTimestamp })
    }
}