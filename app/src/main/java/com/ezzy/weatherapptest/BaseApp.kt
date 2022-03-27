package com.ezzy.weatherapptest

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.ezzy.weatherapptest.work.WeatherWorkManager
import dagger.hilt.android.HiltAndroidApp
import io.sentry.android.core.SentryAndroid
import timber.log.Timber
import zerobranch.androidremotedebugger.AndroidRemoteDebugger
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BaseApp: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidRemoteDebugger.init(applicationContext)
        SentryAndroid.init(this) { options ->
            options.sessionTrackingIntervalMillis = 60000
            options.isEnableSessionTracking = true
        }
        executeCurrencyWork()
    }

    private fun executeCurrencyWork() {

        val workConstraints = Constraints.Builder().apply {
            setRequiresCharging(false)
            setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            setRequiresBatteryNotLow(false)
        }.build()

//        val curRequest = OneTimeWorkRequest.Builder(AppWorkManager::class.java)
//            .setConstraints(workConstraints)
//            .build()

        val budgetRequest = PeriodicWorkRequest.Builder(
            WeatherWorkManager::class.java, 10, TimeUnit.SECONDS
        ).setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "notification_work",
                ExistingPeriodicWorkPolicy.KEEP,
                budgetRequest
            )

    }
}