package com.example.catsbankingapp.presentation.operations

import com.example.catsbankingapp.domain.GetAccountOperationsListUseCase
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapper
import kotlinx.coroutines.CoroutineScope

interface OperationsListPresenterFactory {
    fun create(coroutineScope: CoroutineScope): OperationsListPresenter
}

class OperationsListPresenterFactoryImpl(
    private val getAccountOperationsListUseCase: GetAccountOperationsListUseCase,
    private val accountOperationsScreenModelMapper: AccountOperationsScreenModelMapper
) : OperationsListPresenterFactory {
    override fun create(coroutineScope: CoroutineScope): OperationsListPresenter =
        OperationsListPresenterImpl(
            coroutineScope = coroutineScope,
            getAccountOperationsListUseCase = getAccountOperationsListUseCase,
            accountOperationsScreenModelMapper = accountOperationsScreenModelMapper
        )
}