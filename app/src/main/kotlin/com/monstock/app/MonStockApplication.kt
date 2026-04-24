package com.monstock.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MonStockApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Application-level initialization
    }
}
