package com.example.newsaggregator.data.rss.mappers

import com.example.newsaggregator.data.rss.remote.dto.RssDto
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import java.text.DateFormat.getDateTimeInstance


fun RssDto.toRssFeedItem(): RssFeedItem? {
    val dateFormat = getDateTimeInstance()
    return RssFeedItem(
        idTimestamp = dateFormat.parse(channel.pubDate)?.time ?: return null,
        items = channel.items.map { it.toNewsItem(dateFormat) }
    )
}
