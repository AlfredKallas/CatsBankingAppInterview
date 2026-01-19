package com.example.catsbankingapp.domain.mappers

import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.domain.models.Operation

interface AccountMapper {
    fun toAccount(accountModel: AccountModel, comparator: Comparator<Operation>? = null): Account
    fun toAccountList(accountModelList: List<AccountModel>): List<Account>
}

class AccountMapperImpl(
    private val operationMapper: OperationMapper
) : AccountMapper {
    override fun toAccount(
        accountModel: AccountModel,
        comparator: Comparator<Operation>?
    ): Account =
        Account(
            id = accountModel.id,
            label = accountModel.label,
            balance = accountModel.balance ?: 0.0,
            operations = operationMapper.toOperationList(
                    accountModel.operations,
                    comparator
                )
        )

    override fun toAccountList(accountModelList: List<AccountModel>): List<Account> =
        accountModelList.map { toAccount(it) }

}