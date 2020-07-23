package com.example.paggingsample

import android.app.Application
import com.example.paggingsample.di.networkModule
import com.example.paggingsample.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PaggingSampleApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PaggingSampleApplication)
            androidLogger()
            modules(listOf(viewmodelModule, networkModule))
        }
    }

}