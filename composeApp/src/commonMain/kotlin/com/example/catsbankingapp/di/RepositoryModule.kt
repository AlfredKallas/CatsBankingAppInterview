package com.example.catsbankingapp.di

import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.data.BanksListRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<BanksListRepository> {
        BanksListRepositoryImpl(get())
    }
}