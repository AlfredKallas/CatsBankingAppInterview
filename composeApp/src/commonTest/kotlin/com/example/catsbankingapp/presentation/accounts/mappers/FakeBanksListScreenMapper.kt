package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.BanksList
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.BankSectionUIModel
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel

class FakeBanksListScreenMapper : BanksListScreenMapper {
    override suspend fun mapToUIModel(
        banksList: BanksList,
        accountsPresenterActions: AccountsPresenterActions
    ): BanksListScreenUIModel {
        // Return dummy data
        return BanksListScreenUIModel(
            CABankSection = BankSectionUIModel("CA", emptyList()),
            otherBanksSection = BankSectionUIModel("Other", emptyList())
        )
    }
}
