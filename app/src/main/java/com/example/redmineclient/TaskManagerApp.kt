package com.example.redmineclient

import android.app.Application
import com.example.redmineclient.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TaskManagerApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@TaskManagerApp)
            modules(appModule)
        }
    }
}