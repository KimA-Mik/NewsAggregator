package com.example.newsaggregator.data.rss.remote

import com.example.newsaggregator.data.rss.remote.dto.RssDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RssFeed {
    @GET("/{query}/rss")
    suspend fun getRss(
        @Path("query") query: String = "international"
    ): RssDto
}