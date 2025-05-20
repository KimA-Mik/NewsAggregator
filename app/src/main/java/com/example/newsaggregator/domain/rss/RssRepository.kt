package com.example.newsaggregator.domain.rss

import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.errors.CommonError
import com.example.newsaggregator.domain.rss.model.RssFeedItem

interface RssRepository {
    suspend fun getFreshFeed(): CustomResult<RssFeedItem, CommonError>
}