package com.example.newsaggregator.data.rss.mappers

import com.example.newsaggregator.data.rss.remote.dto.ItemDto
import com.example.newsaggregator.domain.rss.model.NewsItem
import java.text.DateFormat

fun ItemDto.toNewsItem(dateFormat: DateFormat): NewsItem {
    var imageIndex = Int.MIN_VALUE
    var maxWidth = Int.MIN_VALUE
    for (i in contents.indices) {
        val width = contents[i].width?.toIntOrNull() ?: continue
        if (maxWidth < width) {
            maxWidth = width
            imageIndex = i
        }
    }

    return NewsItem(
        guid = guid,
        link = link,
        title = title,
        description = description,
        pubDate = dateFormat.parse(pubDate)?.time,
        categories = categories.map { it.value },
        imageUrl = contents.getOrNull(imageIndex)?.url
    )
}