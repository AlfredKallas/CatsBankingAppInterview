package com.example.catsbankingapp.presentation.operations.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import com.example.catsbankingapp.presentation.operations.models.toUIModelList
import com.example.catsbankingapp.utils.DateTimeFormatter

interface AccountOperationsScreenModelMapper {
    fun toUIModel(
        account: Account
    ): AccountOperationsScreenModel
}

class AccountOperationsScreenModelMapperImpl(
    private val operationUiModelMapper: OperationUiModelMapper,
): AccountOperationsScreenModelMapper {
    override fun toUIModel(
        account: Account
    ): AccountOperationsScreenModel {
        return AccountOperationsScreenModel(
            accountTitle = account.label.orEmpty(),
            totalBalance = account.balance.toString(),
            operations = operationUiModelMapper.toUIModelList(account.operations)
        )
    }

}