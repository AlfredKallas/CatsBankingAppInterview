package com.example.catsbankingapp.presentation.accounts.di

import com.example.catsbankingapp.presentation.accounts.AccountsPresenter
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterImpl
import com.example.catsbankingapp.presentation.accounts.AccountsViewModel
import com.example.catsbankingapp.presentation.accounts.mappers.AccountsUIStateMapper
import com.example.catsbankingapp.presentation.accounts.mappers.AccountsUIStateMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BankSectionMapper
import com.example.catsbankingapp.presentation.accounts.mappers.BankSectionMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BankUIStateMapper
import com.example.catsbankingapp.presentation.accounts.mappers.BankUIStateMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapperImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainScreenModule = module {

    factory<AccountsUIStateMapper> {
        AccountsUIStateMapperImpl()
    }

    factory<BankUIStateMapper> {
        BankUIStateMapperImpl(get())
    }

    factory<BankSectionMapper> {
        BankSectionMapperImpl(get())
    }

    factory<BanksListScreenMapper> {
        BanksListScreenMapperImpl(get())
    }

    factory<AccountsPresenter> {
        AccountsPresenterImpl(get(), get())

    }

    viewModelOf(::AccountsViewModel)
}