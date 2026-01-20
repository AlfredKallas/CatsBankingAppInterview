package com.example.catsbankingapp.presentation.accounts

import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.utils.StringProvider
import kotlinx.coroutines.CoroutineScope

interface AccountsPresenterFactory {
    fun create(coroutineScope: CoroutineScope): AccountsPresenter
}

class AccountsPresenterFactoryImpl(
    private val getBanksListUseCase: GetBanksListUseCase,
    private val banksListScreenMapper: BanksListScreenMapper,
    private val stringProvider: StringProvider
) : AccountsPresenterFactory {
    override fun create(coroutineScope: CoroutineScope): AccountsPresenter =
        AccountsPresenterImpl(
            coroutineScope = coroutineScope,
            getBanksListUseCase = getBanksListUseCase,
            banksListScreenMapper = banksListScreenMapper,
            stringProvider = stringProvider
        )
}