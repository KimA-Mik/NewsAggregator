package com.example.newsaggregator.domain.rss.usecase

import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.errors.CommonError
import com.example.newsaggregator.domain.rss.RssRepository
import com.example.newsaggregator.domain.rss.model.RssFeedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GetLatestRssFeedUseCase @Inject constructor(
    private val repository: RssRepository
) {
    operator fun invoke(): Flow<Result> {
        val loaded = MutableStateFlow(false)
        val localFeed = combine(repository.getCachedFeed(), loaded) { feed, loaded ->
            when (feed) {
                null -> if (loaded) {
                    Result.UnableToLoad
                } else {
                    Result.Loading
                }

                else -> Result.Success(feed)
            }
        }

        val loadingFlow = flow<Result> {
            emit(Result.Loading)

            val fetchResult = repository.fetchFreshFeed()
            emit(Result.LoadingComplete)
            loaded.value = true
            if (fetchResult !is CustomResult.Error) {
                return@flow
            }

            when (fetchResult.data) {
                CommonError.NO_INTERNET -> emit(Result.NoInternetConnection)
                else -> emit(Result.UnknownError)
            }
        }

        return merge(localFeed, loadingFlow)
    }

    sealed interface Result {
        data class Success(val feed: RssFeedItem) : Result
        data object Loading : Result
        data object LoadingComplete : Result
        data object UnableToLoad : Result
        data object NoInternetConnection : Result
        data object UnknownError : Result
    }
}