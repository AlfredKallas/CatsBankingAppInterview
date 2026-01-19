package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Bank
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.BankUIModel
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter

interface BankUIModelMapper {
    fun toUIModel(
        bank: Bank,
        accountsPresenterActions: AccountsPresenterActions
    ): BankUIModel
    fun toUIModelList(
        banks: List<Bank>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<BankUIModel>
}

class BankUIModelMapperImpl(
    private val accountsUIModelMapper: AccountsUIModelMapper,
    private val currencyFormatter: CurrencyFormatter
) : BankUIModelMapper {
    override fun toUIModel(
        bank: Bank,
        accountsPresenterActions: AccountsPresenterActions
    ): BankUIModel {
        val totalAccountsBalance = bank.accounts.sumOf { it.balance }
        val formattedTotalAccountsBalance = currencyFormatter.format(
            amount = totalAccountsBalance
        )
        return BankUIModel(
            title = bank.name.orEmpty(),
            accounts = accountsUIModelMapper.toUIModel(bank.accounts, accountsPresenterActions),
            totalAccountsBalances = formattedTotalAccountsBalance
        )
    }

    override fun toUIModelList(
        banks: List<Bank>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<BankUIModel> {
        return banks.map { toUIModel(it, accountsPresenterActions) }
    }

}