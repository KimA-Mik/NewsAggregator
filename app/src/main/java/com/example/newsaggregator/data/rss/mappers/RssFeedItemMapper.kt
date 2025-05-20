package com.example.newsaggregator.data.rss.mappers

import android.util.Log
import com.example.newsaggregator.data.rss.remote.dto.RssDto
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG = "RssDto.toRssFeedItem"
fun RssDto.toRssFeedItem(): RssFeedItem? {
    val format = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    return try {
        RssFeedItem(
            idTimestamp = format.parse(channel.pubDate)?.time ?: return null,
            items = channel.items.map { it.toNewsItem(format) }
        )
    } catch (e: Exception) {
        Log.d(TAG, e.toString())
        null
    }
}
