package com.example.newsaggregator.data.di

import com.example.newsaggregator.data.rss.RssRepositoryImpl
import com.example.newsaggregator.data.rss.dataSource.RemoteRssDataSource
import com.example.newsaggregator.data.rss.dataSource.RetrofitRssDataSource
import com.example.newsaggregator.data.rss.remote.RssFeed
import com.example.newsaggregator.domain.rss.RssRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RssModule {
    @Provides
    @Singleton
    fun guardianClient(): RssFeed = Retrofit.Builder()
        .baseUrl(RemoteRssDataSource.GUARDIAN_BASE_URL)
        .addConverterFactory(
            XML.asConverterFactory(
                MediaType.get("application/xml; charset=UTF8")
            )
        ).build()
        .create(RssFeed::class.java)

    @Provides
    @Singleton
    fun remoteRssDataSource(rssFeed: RssFeed) = RetrofitRssDataSource(rssFeed)

    @Provides
    @Singleton
    fun rssRepository(remoteRssDataSource: RemoteRssDataSource) =
        RssRepositoryImpl(remoteRssDataSource)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RssBindModule {
    @Binds
    abstract fun bindRemoteRssDataSource(retrofitRssDataSource: RetrofitRssDataSource): RemoteRssDataSource

    @Binds
    abstract fun bindRssRepository(rssRepositoryIml: RssRepositoryImpl): RssRepository
}