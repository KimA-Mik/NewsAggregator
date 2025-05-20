package com.example.newsaggregator.data.rss.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsaggregator.data.rss.local.dao.GuardianRssFeedDao
import com.example.newsaggregator.data.rss.local.entities.GuardianNewsEntity
import com.example.newsaggregator.data.rss.local.entities.GuardianRssFeedEntity

@Database(
    entities = [
        GuardianNewsEntity::class,
        GuardianRssFeedEntity::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun guardianRssDao(): GuardianRssFeedDao

    companion object {
        const val NAME = "cache_database"
    }
}