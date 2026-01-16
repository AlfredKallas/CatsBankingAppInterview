package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.HttpClientFactory
import org.koin.dsl.module

val AppModule = module {
    single {
        HttpClientFactory()
    }
    single {
        get<HttpClientFactory>().create(get(), get())
    }
}