package com.eduardocodigo0

import android.app.Application
import com.eduardocodigo0.modules.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JokeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@JokeApplication)
            modules(mainModule)
        }
    }
}