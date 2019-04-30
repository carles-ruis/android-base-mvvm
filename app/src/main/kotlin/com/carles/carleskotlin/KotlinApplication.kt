package com.carles.carleskotlin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KotlinApplication)
            modules(modules)
        }
    }
}