package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.AccountUIModel

interface AccountsUIModelMapper {
    fun toUIModel(
        accounts: List<Account>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<AccountUIModel>
}

class AccountsUIModelMapperImpl : AccountsUIModelMapper{
    override fun toUIModel(
        accounts: List<Account>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<AccountUIModel> {
        return accounts.map { account ->
            AccountUIModel(
                title = account.label.orEmpty(),
                accountBalance = account.balance.toString(),
                onClick = {
                    accountsPresenterActions.onAccountClicked(account.id.orEmpty())
                }
            )
        }
    }
}