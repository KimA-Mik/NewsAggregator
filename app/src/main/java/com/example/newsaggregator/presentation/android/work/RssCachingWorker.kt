package com.example.newsaggregator.presentation.android.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsaggregator.domain.common.CustomResult
import com.example.newsaggregator.domain.rss.RssRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RssCachingWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: RssRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return when (repository.fetchFreshFeed()) {
            is CustomResult.Error<*, *> -> Result.failure()
            is CustomResult.Success<*, *> -> Result.success()
        }
    }

    companion object {
        const val TAG = "RssCachingWorker"
    }
}