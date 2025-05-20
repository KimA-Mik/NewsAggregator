package com.example.newsaggregator.data.rss.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsaggregator.data.rss.local.entities.GuardianFeedWithNews
import com.example.newsaggregator.data.rss.local.entities.GuardianNewsEntity
import com.example.newsaggregator.data.rss.local.entities.GuardianRssFeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GuardianRssFeedDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeed(feed: GuardianRssFeedEntity, newsEntity: List<GuardianNewsEntity>)

    @Transaction
    @Query(
        "SELECT * FROM ${GuardianRssFeedEntity.TABLE_NAME} " +
                "ORDER BY ${GuardianRssFeedEntity.PRIMARY_COLUMN_NAME} DESC LIMIT 1"
    )
    fun subscribeToLatestFeed(): Flow<GuardianFeedWithNews?>

    @Query(
        "SELECT * FROM ${GuardianRssFeedEntity.TABLE_NAME} " +
                "ORDER BY ${GuardianRssFeedEntity.PRIMARY_COLUMN_NAME} ASC"
    )
    suspend fun getAllFeeds(): List<GuardianRssFeedEntity>

    @Query(
        "DELETE FROM ${GuardianRssFeedEntity.TABLE_NAME} " +
                "WHERE ${GuardianRssFeedEntity.PRIMARY_COLUMN_NAME} IN (:feeds)"
    )
    suspend fun deleteFeeds(feeds: List<Long>)

    @Query(
        "DELETE FROM ${GuardianNewsEntity.TABLE_NAME} " +
                "WHERE ${GuardianNewsEntity.FEED_ID_COLUMN_NAME} IN (:feeds)"
    )
    suspend fun deleteNewsForFeeds(feeds: List<Long>)

    @Transaction
    suspend fun deleteFeedsTransaction(feeds: List<Long>) {
        deleteFeeds(feeds)
        deleteNewsForFeeds(feeds)
    }
}