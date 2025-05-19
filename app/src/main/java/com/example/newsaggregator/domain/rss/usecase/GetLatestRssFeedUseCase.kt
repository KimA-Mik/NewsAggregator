package com.example.newsaggregator.domain.rss.usecase

import com.example.newsaggregator.domain.rss.RssRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLatestRssFeedUseCase @Inject constructor(
    private val repository: RssRepository
) {
    operator fun invoke() = flow<Int> {

    }
}