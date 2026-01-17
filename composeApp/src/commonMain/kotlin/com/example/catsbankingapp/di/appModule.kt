package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.network.AppConfig
import com.example.catsbankingapp.domain.di.useCasesModule
import com.example.catsbankingapp.presentation.accounts.di.accountsScreenModule
import com.example.catsbankingapp.presentation.operations.di.accountOperationsScreenModule

fun appModule(appConfig: AppConfig) =
    listOf(
        networkModule(appConfig = appConfig),
        localStorageModule,
        dateTimeModule,
        repositoryModule,
        useCasesModule,
        accountsScreenModule,
        accountOperationsScreenModule
    )