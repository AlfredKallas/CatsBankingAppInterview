package com.example.catsbankingapp.di

import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter
import com.example.catsbankingapp.utils.currencyformatter.buildCurrencyFormatter
import org.koin.dsl.module

val utilsModule = module {
    single<CurrencyFormatter> {
        buildCurrencyFormatter()
    }
}