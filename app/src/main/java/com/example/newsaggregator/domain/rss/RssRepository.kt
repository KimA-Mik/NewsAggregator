package com.example.newsaggregator.domain.rss

import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.errors.CommonError
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import kotlinx.coroutines.flow.Flow

interface RssRepository {
    suspend fun fetchFreshFeed(): CustomResult<RssFeedItem, CommonError>
    fun subscribeToLastCachedFeed(): Flow<RssFeedItem?>
    suspend fun getCachedFeedsIds(): List<Long>
    suspend fun deleteFeeds(feeds: List<Long>)
}