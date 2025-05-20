package com.example.newsaggregator.data.rss.local.mappers

import com.example.newsaggregator.data.rss.local.entities.GuardianFeedWithNews
import com.example.newsaggregator.data.rss.local.entities.GuardianRssFeedEntity
import com.example.newsaggregator.domain.rss.model.RssFeedItem

fun RssFeedItem.toEntity() = GuardianFeedWithNews(
    feed = GuardianRssFeedEntity(idTimestamp),
    news = items.map { it.toEntity(idTimestamp) }
)

fun GuardianFeedWithNews.toItem() = RssFeedItem(
    idTimestamp = feed.idTimestamp,
    items = news.map { it.toItem() }
)