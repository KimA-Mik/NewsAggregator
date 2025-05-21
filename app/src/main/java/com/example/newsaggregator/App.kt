package com.example.newsaggregator

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsaggregator.presentation.android.work.CleanUpWorker
import com.example.newsaggregator.presentation.android.work.RssCachingWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        registerWorkers()
    }


    private fun registerWorkers() {
        val workManager = WorkManager.getInstance(this)

        val cleanupWorkRequest = PeriodicWorkRequestBuilder<CleanUpWorker>(1, TimeUnit.DAYS)
            .addTag(CleanUpWorker.TAG)
            .setInitialDelay(30, TimeUnit.MINUTES)
            .build()
        workManager.enqueueUniquePeriodicWork(
            CleanUpWorker.TAG,
            ExistingPeriodicWorkPolicy.REPLACE,
            cleanupWorkRequest
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()
        val cachingWorkRequest = PeriodicWorkRequestBuilder<RssCachingWorker>(6, TimeUnit.HOURS)
            .addTag(RssCachingWorker.TAG)
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            RssCachingWorker.TAG,
            ExistingPeriodicWorkPolicy.UPDATE,
            cachingWorkRequest
        )
    }
}