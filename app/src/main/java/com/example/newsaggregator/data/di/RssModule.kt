package com.example.newsaggregator.data.di

import android.content.Context
import androidx.room.Room
import com.example.newsaggregator.data.rss.RssRepositoryImpl
import com.example.newsaggregator.data.rss.datasource.LocalRssCacheDataSource
import com.example.newsaggregator.data.rss.datasource.RemoteRssDataSource
import com.example.newsaggregator.data.rss.datasource.RetrofitRssDataSource
import com.example.newsaggregator.data.rss.datasource.RoomRssCacheDataSource
import com.example.newsaggregator.data.rss.local.Database
import com.example.newsaggregator.data.rss.remote.RssFeed
import com.example.newsaggregator.domain.rss.RssRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

//    @Provides
//    @Singleton
//    fun remoteRssDataSource(rssFeed: RssFeed) = RetrofitRssDataSource(rssFeed)
//
//    @Provides
//    @Singleton
//    fun rssRepository(
//        remoteRssDataSource: RemoteRssDataSource,
//        localRssCacheDataSource: LocalRssCacheDataSource
//    ) = RssRepositoryImpl(
//        remoteRssDataSource = remoteRssDataSource,
//        localRssCacheDataSource = localRssCacheDataSource
//    )

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): Database = Room
        .databaseBuilder(
            context,
            Database::class.java,
            Database.NAME
        ).build()

//    @Provides
//    @Singleton
//    fun localRssCacheDataSource(database: Database) = RoomRssCacheDataSource(database)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RssBindModule {
    @Binds
    abstract fun bindRemoteRssDataSource(retrofitRssDataSource: RetrofitRssDataSource): RemoteRssDataSource

    @Binds
    abstract fun bindLocalRssCacheDataSource(roomRssCacheDataSource: RoomRssCacheDataSource): LocalRssCacheDataSource

    @Binds
    abstract fun bindRssRepository(rssRepositoryIml: RssRepositoryImpl): RssRepository
}