package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.AppConfig
import com.example.catsbankingapp.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module


fun networkModule(appConfig: AppConfig) = module {
    // 1. Provide AppConfig (so we can inject it elsewhere if needed)
    single { appConfig }
    // 2. Provide Json
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
    }
    // 3. Provide HttpClient
    single<HttpClient> {
        HttpClientFactory().create(get(), get())
    }
}