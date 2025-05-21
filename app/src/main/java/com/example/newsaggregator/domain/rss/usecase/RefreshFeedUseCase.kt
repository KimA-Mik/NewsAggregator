package com.example.newsaggregator.domain.rss.usecase

import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.errors.CommonError
import com.example.newsaggregator.domain.rss.RssRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshFeedUseCase @Inject constructor(
    private val repository: RssRepository
) {
    operator fun invoke() = flow<Result> {
        emit(Result.Loading)
        val response = repository.fetchFreshFeed()
        when (response) {
            is CustomResult.Success<*, *> -> emit(Result.Success)
            is CustomResult.Error<*, CommonError> -> when (response.data) {
                CommonError.NO_INTERNET -> emit(Result.NoInternet)
                else -> emit(Result.UnknownError)
            }
        }
    }

    sealed interface Result {
        data object Success : Result
        data object Loading : Result
        data object NoInternet : Result
        data object UnknownError : Result
    }
}