package com.example.newsaggregator.data.rss.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GuardianRssFeedEntity.TABLE_NAME)
data class GuardianRssFeedEntity(
    @PrimaryKey
    @ColumnInfo(name = PRIMARY_COLUMN_NAME)
    val idTimestamp: Long
) {
    companion object {
        const val TABLE_NAME = "guardian_rss_feed"
        const val PRIMARY_COLUMN_NAME = "id_timestamp"
    }
}
