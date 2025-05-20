package com.example.newsaggregator.data.rss

import com.example.newsaggregator.data.rss.dataSource.RemoteRssDataSource
import com.example.newsaggregator.data.rss.mappers.toRssFeedItem
import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.errors.CommonError
import com.example.newsaggregator.domain.rss.RssRepository
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import java.io.IOException
import javax.inject.Inject

class RssRepositoryImpl @Inject constructor(
    private val remoteRssDataSource: RemoteRssDataSource
) : RssRepository {
    override suspend fun getFreshFeed(): CustomResult<RssFeedItem, CommonError> = try {
        when (val result = remoteRssDataSource.getGuardianRss().toRssFeedItem()) {
            null -> CustomResult.Error(CommonError.DATA_ERROR)
            else -> CustomResult.Success(result)
        }
    } catch (_: IOException) {
        CustomResult.Error(CommonError.NO_INTERNET)
    } catch (_: Exception) {
        CustomResult.Error(CommonError.UNKNOWN_ERROR)
    }
}