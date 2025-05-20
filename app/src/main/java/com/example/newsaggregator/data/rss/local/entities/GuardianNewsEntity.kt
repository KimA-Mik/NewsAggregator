package com.example.newsaggregator.data.rss.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newsaggregator.data.util.typeconverter.JsonStringListConverter

@Entity(tableName = GuardianNewsEntity.TABLE_NAME)
@TypeConverters(JsonStringListConverter::class)
data class GuardianNewsEntity(
    @PrimaryKey
    val guid: String,
    val link: String,
    val title: String,
    val description: String,
    @ColumnInfo(name = "publication_date")
    val pubDate: Long?,
    val categories: List<String>,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    @ColumnInfo(name = FEED_ID_COLUMN_NAME)
    val associatedFeed: Long
) {
    companion object {
        const val TABLE_NAME = "guardian_news"
        const val FEED_ID_COLUMN_NAME = "associated_feed"
    }
}