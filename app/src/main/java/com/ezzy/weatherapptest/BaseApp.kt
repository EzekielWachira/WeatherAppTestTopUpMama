package com.ezzy.weatherapptest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.sentry.android.core.SentryAndroid
import timber.log.Timber
import zerobranch.androidremotedebugger.AndroidRemoteDebugger

@HiltAndroidApp
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidRemoteDebugger.init(applicationContext)
        SentryAndroid.init(this) { options ->
            options.sessionTrackingIntervalMillis = 60000
            options.isEnableSessionTracking = true
        }
    }
}