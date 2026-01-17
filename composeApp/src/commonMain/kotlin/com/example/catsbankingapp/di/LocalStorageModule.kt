package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.local.LocalBanksListDataSource
import com.example.catsbankingapp.core.local.LocalBanksListDataSourceImpl
import org.koin.dsl.module

val localStorageModule = module {
    single<LocalBanksListDataSource> {
        LocalBanksListDataSourceImpl()
    }
}