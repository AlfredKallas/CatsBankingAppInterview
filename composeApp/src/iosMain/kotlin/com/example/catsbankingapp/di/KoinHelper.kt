package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.AppConfig
import org.koin.core.context.startKoin

fun initKoin(
    isDebug: Boolean,
    baseUrl: String
) {
    val config = AppConfig(
        isDebug = isDebug,
        baseUrl = baseUrl,
    )

    startKoin {
        modules(networkModule(config))
    }
}