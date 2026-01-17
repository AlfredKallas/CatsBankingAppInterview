package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.AppConfig
import com.example.catsbankingapp.domain.di.useCasesModule
import com.example.catsbankingapp.presentation.mainScreen.di.mainScreenModule

fun appModule(appConfig: AppConfig) =
    listOf(
        networkModule(appConfig = appConfig),
        dateTimeModule,
        repositoryModule,
        useCasesModule,
        mainScreenModule
    )