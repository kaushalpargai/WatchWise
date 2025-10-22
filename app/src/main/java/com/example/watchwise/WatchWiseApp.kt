package com.example.watchwise

import android.app.Application
import android.content.Context
import com.example.watchwise.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WatchWiseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WatchWiseApp)
            modules(appModule)
        }
    }
}

// Extension property to get application context from anywhere
val Context.app: WatchWiseApp
    get() = applicationContext as WatchWiseApp
