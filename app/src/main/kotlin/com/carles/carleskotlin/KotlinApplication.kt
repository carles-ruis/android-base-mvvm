package com.carles.carleskotlin

import android.app.Application
import org.koin.android.ext.android.startKoin

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules)
    }
}