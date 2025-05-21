package com.example.newsaggregator.data.rss.mappers

import android.util.Log
import com.example.newsaggregator.data.rss.remote.dto.RssDto
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import java.text.DateFormat

private const val TAG = "RssDto.toRssFeedItem"
fun RssDto.toRssFeedItem(dateFormat: DateFormat): RssFeedItem? {
    return try {
        RssFeedItem(
            idTimestamp = dateFormat.parse(channel.pubDate)?.time ?: return null,
            items = channel.items.map { it.toNewsItem(dateFormat) }
        )
    } catch (e: Exception) {
        Log.d(TAG, e.toString())
        null
    }
}
