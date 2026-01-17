package com.example.catsbankingapp

import android.app.Application
import com.example.catsbankingapp.core.network.AppConfig
import com.example.catsbankingapp.di.initKoin

class CatsBankingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val appConfig = AppConfig(
            isDebug = BuildConfig.DEBUG,
            baseUrl = BuildConfig.BASE_URL
        )
        initKoin(this, appConfig)
    }
}