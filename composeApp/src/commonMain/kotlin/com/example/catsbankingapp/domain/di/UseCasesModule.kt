package com.example.catsbankingapp.domain.di

import com.example.catsbankingapp.domain.GetBanksListUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GetBanksListUseCase(get(), get())
    }
}