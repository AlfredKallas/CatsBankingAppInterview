package com.example.catsbankingapp.domain.di

import com.example.catsbankingapp.domain.GetAccountOperationsListUseCase
import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.domain.mappers.AccountMapper
import com.example.catsbankingapp.domain.mappers.AccountMapperImpl
import com.example.catsbankingapp.domain.mappers.BankMapper
import com.example.catsbankingapp.domain.mappers.BankMapperImpl
import com.example.catsbankingapp.domain.mappers.OperationMapper
import com.example.catsbankingapp.domain.mappers.OperationMapperImpl
import org.koin.dsl.module

val domainModule = module {

    factory<OperationMapper> {
        OperationMapperImpl(get())
    }

    factory<AccountMapper> {
        AccountMapperImpl(get())
    }

    factory<BankMapper> {
        BankMapperImpl(get())
    }

    factory {
        GetBanksListUseCase(
            banksListRepository = get(),
            bankMapper = get()
        )
    }

    factory {
        GetAccountOperationsListUseCase(
            accountsRepository = get(),
            accountMapper = get()
        )
    }
}