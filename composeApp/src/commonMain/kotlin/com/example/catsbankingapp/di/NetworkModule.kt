package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.AppConfig
import com.example.catsbankingapp.core.network.NetworkClient
import com.example.catsbankingapp.core.network.buildHttpClientFactory
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
        buildHttpClientFactory().create(get(), get())
    }

    single {
        NetworkClient(Dispatchers.IO, get(), get())
    }
}