package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.BanksList
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel

interface BanksListScreenMapper {
    fun mapToUIModel(
        banksList: BanksList,
        accountsPresenterActions: AccountsPresenterActions
    ): BanksListScreenUIModel
}

class BanksListScreenMapperImpl(
    private val bankSectionMapper: BankSectionMapper,
) : BanksListScreenMapper {
    override fun mapToUIModel(
        banksList: BanksList,
        accountsPresenterActions: AccountsPresenterActions
    ): BanksListScreenUIModel {
        return BanksListScreenUIModel(
            CABankSection =
                bankSectionMapper.toUIModel(
                    "CABanks",
                    banksList.CABanks,
                    accountsPresenterActions
                ), //TODO: Change title to resources afterwards
            otherBanksSection =
                bankSectionMapper.toUIModel(
                    "Other Banks",
                    banksList.otherBanks,
                    accountsPresenterActions
                ) //TODO: Change title to resources afterwards
        )
    }

}