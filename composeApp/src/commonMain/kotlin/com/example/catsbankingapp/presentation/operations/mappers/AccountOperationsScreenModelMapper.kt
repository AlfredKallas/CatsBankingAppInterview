package com.example.catsbankingapp.presentation.operations.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter

interface AccountOperationsScreenModelMapper {
    fun toUIModel(
        account: Account
    ): AccountOperationsScreenModel
}

class AccountOperationsScreenModelMapperImpl(
    private val operationUiModelMapper: OperationUiModelMapper,
    private val currencyFormatter: CurrencyFormatter
): AccountOperationsScreenModelMapper {
    override fun toUIModel(
        account: Account
    ): AccountOperationsScreenModel {
        val totalBalanceFormatter = currencyFormatter.format(
            amount = account.balance
        )
        return AccountOperationsScreenModel(
            accountTitle = account.label.orEmpty(),
            totalBalance = totalBalanceFormatter,
            operations = operationUiModelMapper.toUIModelList(account.operations.orEmpty())
        )
    }

}