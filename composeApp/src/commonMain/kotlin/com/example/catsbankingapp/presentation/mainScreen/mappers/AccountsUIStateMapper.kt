package com.example.catsbankingapp.presentation.mainScreen.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.presentation.mainScreen.models.AccountUIModel

interface AccountsUIStateMapper {
    fun toUIModel(accounts: List<Account>): List<AccountUIModel>
}

class AccountsUIStateMapperImpl : AccountsUIStateMapper{
    override fun toUIModel(accounts: List<Account>): List<AccountUIModel> {
        return accounts.map { account ->
            AccountUIModel(
                title = account.label.orEmpty(),
                accountBalance = account.balance.toString(),
                onClick = { /*TODO*/ }
            )
        }
    }
}