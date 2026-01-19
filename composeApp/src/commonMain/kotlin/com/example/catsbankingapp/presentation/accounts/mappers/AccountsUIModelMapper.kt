package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.AccountUIModel
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter

interface AccountsUIModelMapper {
    fun toUIModel(
        accounts: List<Account>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<AccountUIModel>
}

class AccountsUIModelMapperImpl(
    private val currencyFormatter: CurrencyFormatter
) : AccountsUIModelMapper{
    override fun toUIModel(
        accounts: List<Account>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<AccountUIModel> {
        return accounts.map { account ->
            val accountBalance = account.balance
            val formattedAccountBalance = currencyFormatter.format(
                amount = accountBalance
            )
            AccountUIModel(
                title = account.label.orEmpty(),
                accountBalance = formattedAccountBalance,
                onClick = {
                    accountsPresenterActions.onAccountClicked(account.id.orEmpty())
                }
            )
        }
    }
}