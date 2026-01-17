package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Bank
import com.example.catsbankingapp.presentation.accounts.models.BankUIModel

interface BankUIStateMapper {
    fun toUIModel(bank: Bank): BankUIModel
    fun toUIModelList(banks: List<Bank>): List<BankUIModel>
}

class BankUIStateMapperImpl(private val accountsUIStateMapper: AccountsUIStateMapper) : BankUIStateMapper {
    override fun toUIModel(bank: Bank): BankUIModel {
        return BankUIModel(
            title = bank.name.orEmpty(),
            accounts = accountsUIStateMapper.toUIModel(bank.accounts),
            totalAccountsBalances = bank.accounts.sumOf { it.balance ?: 0.0 }.toString()
        )
    }

    override fun toUIModelList(banks: List<Bank>): List<BankUIModel> {
        return banks.map { toUIModel(it) }
    }

}