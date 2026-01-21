package com.example.catsbankingapp.presentation.accounts.di

import com.example.catsbankingapp.presentation.accounts.AccountsPresenterFactory
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterFactoryImpl
import com.example.catsbankingapp.presentation.accounts.AccountsViewModel
import com.example.catsbankingapp.presentation.accounts.mappers.AccountsUIModelMapper
import com.example.catsbankingapp.presentation.accounts.mappers.AccountsUIModelMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BankSectionMapper
import com.example.catsbankingapp.presentation.accounts.mappers.BankSectionMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BankUIModelMapper
import com.example.catsbankingapp.presentation.accounts.mappers.BankUIModelMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapperImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val accountsScreenModule = module {

    factory<AccountsUIModelMapper> {
        AccountsUIModelMapperImpl(get())
    }

    factory<BankUIModelMapper> {
        BankUIModelMapperImpl(get(), get())
    }

    factory<BankSectionMapper> {
        BankSectionMapperImpl(get())
    }

    factory<BanksListScreenMapper> {
        BanksListScreenMapperImpl(get(), get())
    }

    factory<AccountsPresenterFactory> {
        AccountsPresenterFactoryImpl(get(), get(), get(), get())
    }

    viewModelOf(::AccountsViewModel)
}