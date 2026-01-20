package com.example.catsbankingapp.presentation.operations.di

import com.example.catsbankingapp.presentation.operations.OperationsListPresenterFactory
import com.example.catsbankingapp.presentation.operations.OperationsListPresenterFactoryImpl
import com.example.catsbankingapp.presentation.operations.OperationsListViewModel
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapper
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapperImpl
import com.example.catsbankingapp.presentation.operations.mappers.OperationUiModelMapper
import com.example.catsbankingapp.presentation.operations.mappers.OperationUiModelMapperImpl
import com.example.catsbankingapp.utils.DefaultNavArgsProvider
import com.example.catsbankingapp.utils.NavArgsProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val accountOperationsScreenModule = module {

    factory<OperationUiModelMapper> {
        OperationUiModelMapperImpl(
            dateFormatter = get(),
            currencyFormatter = get()
        )
    }

    factory<AccountOperationsScreenModelMapper> {
        AccountOperationsScreenModelMapperImpl(
            operationUiModelMapper = get(),
            currencyFormatter = get()
        )
    }

    factory<OperationsListPresenterFactory> {
        OperationsListPresenterFactoryImpl(get(), get())
    }

    viewModelOf(::OperationsListViewModel)
    factory<NavArgsProvider> {
        DefaultNavArgsProvider(get())
    }
}