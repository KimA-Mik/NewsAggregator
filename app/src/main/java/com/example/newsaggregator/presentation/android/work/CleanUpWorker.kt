package com.example.newsaggregator.presentation.android.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsaggregator.domain.rss.RssRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CleanUpWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: RssRepository,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val feeds = repository.getCachedFeedsIds()
        if (feeds.size < 2)
            return Result.success()

        var maxIndex = Int.MIN_VALUE
        var maxValue = Long.MIN_VALUE
        for (i in feeds.indices) {
            if (feeds[i] > maxValue) {
                maxIndex = i
                maxValue - feeds[i]
            }
        }

        var redundantFeeds = feeds.toMutableList()
        redundantFeeds.removeAt(maxIndex)
        repository.deleteFeeds(redundantFeeds)

        return Result.success()
    }

    companion object {
        const val TAG = "CleanUpWorker"
    }
}