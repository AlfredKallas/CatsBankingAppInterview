package com.example.catsbankingapp.presentation.mainScreen.di

import com.example.catsbankingapp.presentation.mainScreen.MainScreenPresenter
import com.example.catsbankingapp.presentation.mainScreen.MainScreenPresenterImpl
import com.example.catsbankingapp.presentation.mainScreen.MainScreenViewModel
import com.example.catsbankingapp.presentation.mainScreen.mappers.AccountsUIStateMapper
import com.example.catsbankingapp.presentation.mainScreen.mappers.AccountsUIStateMapperImpl
import com.example.catsbankingapp.presentation.mainScreen.mappers.BankSectionMapper
import com.example.catsbankingapp.presentation.mainScreen.mappers.BankSectionMapperImpl
import com.example.catsbankingapp.presentation.mainScreen.mappers.BankUIStateMapper
import com.example.catsbankingapp.presentation.mainScreen.mappers.BankUIStateMapperImpl
import com.example.catsbankingapp.presentation.mainScreen.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.mainScreen.mappers.BanksListScreenMapperImpl
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

    factory<MainScreenPresenter> {
        MainScreenPresenterImpl(get(), get())

    }

    viewModelOf(::MainScreenViewModel)
}