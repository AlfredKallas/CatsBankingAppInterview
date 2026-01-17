package com.example.catsbankingapp.di

import android.content.Context
import com.example.catsbankingapp.core.network.AppConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun initKoin(applicationContext: Context, appConfig: AppConfig) {
    startKoin {
        androidContext(applicationContext)
        modules(appModule(appConfig))
    }
}