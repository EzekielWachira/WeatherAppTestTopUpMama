package com.ezzy.weatherapptest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import zerobranch.androidremotedebugger.AndroidRemoteDebugger

@HiltAndroidApp
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidRemoteDebugger.init(applicationContext)
    }
}