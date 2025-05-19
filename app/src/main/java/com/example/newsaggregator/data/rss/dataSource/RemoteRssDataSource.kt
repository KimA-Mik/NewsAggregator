package com.example.newsaggregator.data.rss.dataSource

import com.example.newsaggregator.data.rss.remote.dto.RssDto

interface RemoteRssDataSource {
    suspend fun getGuardianRss(): RssDto

    companion object {
        const val GUARDIAN_BASE_URL = "https://www.theguardian.com"
    }
}