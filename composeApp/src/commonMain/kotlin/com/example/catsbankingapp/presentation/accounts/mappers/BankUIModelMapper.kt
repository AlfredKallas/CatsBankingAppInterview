package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Bank
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.BankUIModel

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

class BankUIModelMapperImpl(private val accountsUIModelMapper: AccountsUIModelMapper) : BankUIModelMapper {
    override fun toUIModel(
        bank: Bank,
        accountsPresenterActions: AccountsPresenterActions
    ): BankUIModel {
        return BankUIModel(
            title = bank.name.orEmpty(),
            accounts = accountsUIModelMapper.toUIModel(bank.accounts, accountsPresenterActions),
            totalAccountsBalances = bank.accounts.sumOf { it.balance ?: 0.0 }.toString()
        )
    }

    override fun toUIModelList(
        banks: List<Bank>,
        accountsPresenterActions: AccountsPresenterActions
    ): List<BankUIModel> {
        return banks.map { toUIModel(it, accountsPresenterActions) }
    }

}