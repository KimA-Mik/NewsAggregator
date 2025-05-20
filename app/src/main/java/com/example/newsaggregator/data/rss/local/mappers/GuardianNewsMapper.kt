package com.example.newsaggregator.data.rss.local.mappers

import com.example.newsaggregator.data.rss.local.entities.GuardianNewsEntity
import com.example.newsaggregator.domain.rss.model.NewsItem

fun NewsItem.toEntity(feedId: Long) = GuardianNewsEntity(
    guid = guid,
    link = link,
    title = title,
    description = description,
    pubDate = pubDate,
    categories = categories,
    imageUrl = imageUrl,
    associatedFeed = feedId
)

fun GuardianNewsEntity.toItem() = NewsItem(
    guid = guid,
    link = link,
    title = title,
    description = description,
    pubDate = pubDate,
    categories = categories,
    imageUrl = imageUrl
)