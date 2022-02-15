package com.example.sendwavecodingtest

import android.app.Application
import com.example.sendwavecodingtest.di.getKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    open fun startKoin() {
        startKoin {
            androidContext(this@AppApplication)
            modules(modules = getKoinModules())
        }
    }
}