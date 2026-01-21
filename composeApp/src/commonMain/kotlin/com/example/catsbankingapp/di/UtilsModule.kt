package com.example.catsbankingapp.di

import com.example.catsbankingapp.utils.DefaultDispatchersProvider
import com.example.catsbankingapp.utils.DispatchersProvider
import com.example.catsbankingapp.utils.StringProvider
import com.example.catsbankingapp.utils.StringProviderImpl
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter
import com.example.catsbankingapp.utils.currencyformatter.buildCurrencyFormatter
import org.koin.dsl.module

val utilsModule = module {
    single<CurrencyFormatter> {
        buildCurrencyFormatter()
    }
    single<StringProvider> {
        StringProviderImpl()
    }
    single<DispatchersProvider> {
        DefaultDispatchersProvider()
    }
}