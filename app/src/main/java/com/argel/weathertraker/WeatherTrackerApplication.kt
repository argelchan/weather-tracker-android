package com.argel.weathertraker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}