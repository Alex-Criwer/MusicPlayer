package com.example.musicplayerapp.app

import android.app.Application
import com.example.musicplayerapp.di.dataModule
import com.example.musicplayerapp.di.domainModule
import com.example.musicplayerapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, presentationModule))
        }
    }
}