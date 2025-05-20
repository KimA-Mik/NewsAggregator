package com.example.newsaggregator.data.rss

import android.util.Log
import com.example.newsaggregator.data.rss.datasource.LocalRssCacheDataSource
import com.example.newsaggregator.data.rss.datasource.RemoteRssDataSource
import com.example.newsaggregator.data.rss.local.mappers.toEntity
import com.example.newsaggregator.data.rss.local.mappers.toItem
import com.example.newsaggregator.data.rss.mappers.toRssFeedItem
import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.errors.CommonError
import com.example.newsaggregator.domain.rss.RssRepository
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class RssRepositoryImpl @Inject constructor(
    private val remoteRssDataSource: RemoteRssDataSource,
    private val localRssCacheDataSource: LocalRssCacheDataSource
) : RssRepository {
    override suspend fun fetchFreshFeed(): CustomResult<RssFeedItem, CommonError> = try {
        when (val result = remoteRssDataSource.getGuardianRss().toRssFeedItem()) {
            null -> CustomResult.Error(CommonError.DATA_ERROR)
            else -> {
                localRssCacheDataSource.insertFeed(result.toEntity())
                CustomResult.Success(result)
            }
        }
    } catch (_: IOException) {
        CustomResult.Error(CommonError.NO_INTERNET)
    } catch (e: Exception) {
        Log.d(TAG, e.toString())
        CustomResult.Error(CommonError.UNKNOWN_ERROR)
    }

    override fun getCachedFeed(): Flow<RssFeedItem?> =
        localRssCacheDataSource.getLatestFeed().map { it?.toItem() }

    companion object {
        private const val TAG = "RssRepositoryImpl"
    }
}