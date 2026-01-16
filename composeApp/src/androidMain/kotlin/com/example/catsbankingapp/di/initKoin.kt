package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.AppConfig
import org.koin.core.context.startKoin

fun initKoin(appConfig: AppConfig) {
    startKoin {
        modules(networkModule(appConfig))
    }
}