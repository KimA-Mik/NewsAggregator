package com.example.newsaggregator.data.rss.datasource

import com.example.newsaggregator.data.rss.local.entities.GuardianFeedWithNews
import kotlinx.coroutines.flow.Flow

interface LocalRssCacheDataSource {
    suspend fun insertFeed(feed: GuardianFeedWithNews)
    fun getLatestFeed(): Flow<GuardianFeedWithNews?>
    suspend fun getAllCachedFeeds(): List<Long>
    suspend fun deleteFeedsTransaction(feeds: List<Long>)
}