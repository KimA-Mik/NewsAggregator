package com.example.newsaggregator.domain.rss.model

data class RssFeedItem(
    val idTimestamp: Long,
    val items: List<NewsItem>
) {
    companion object {
        fun default(idTimestamp: Long = 0, items: List<NewsItem> = emptyList()) =
            RssFeedItem(idTimestamp = idTimestamp, items = items)
    }
}
