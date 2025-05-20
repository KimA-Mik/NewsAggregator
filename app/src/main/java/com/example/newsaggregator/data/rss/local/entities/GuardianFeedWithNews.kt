package com.example.newsaggregator.data.rss.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class GuardianFeedWithNews(
    @Embedded val feed: GuardianRssFeedEntity,
    @Relation(
        parentColumn = GuardianRssFeedEntity.PRIMARY_COLUMN_NAME,
        entityColumn = GuardianNewsEntity.FEED_ID_COLUMN_NAME
    )
    val news: List<GuardianNewsEntity>
)
