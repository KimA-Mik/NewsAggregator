package com.example.newsaggregator.data.rss.datasource

import com.example.newsaggregator.data.rss.local.entities.GuardianFeedWithNews
import com.example.newsaggregator.data.rss.local.entities.GuardianRssFeedEntity
import kotlinx.coroutines.flow.Flow

interface LocalRssCacheDataSource {
    suspend fun insertFeed(feed: GuardianFeedWithNews)
    fun getLatestFeed(): Flow<GuardianFeedWithNews?>
    suspend fun getAllFeeds(): List<GuardianRssFeedEntity>
    suspend fun deleteFeedsTransaction(feeds: List<GuardianRssFeedEntity>)
}