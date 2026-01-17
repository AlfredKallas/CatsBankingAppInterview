package com.example.catsbankingapp.presentation.accounts.mappers

import catsbankingapp.composeapp.generated.resources.Accounts_List_Screen_CA_Section_Title
import catsbankingapp.composeapp.generated.resources.Accounts_List_Screen_Other_Banks_Section_Title
import catsbankingapp.composeapp.generated.resources.Res
import com.example.catsbankingapp.domain.models.BanksList
import com.example.catsbankingapp.presentation.accounts.AccountsPresenterActions
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import org.jetbrains.compose.resources.getString

interface BanksListScreenMapper {
    suspend fun mapToUIModel(
        banksList: BanksList,
        accountsPresenterActions: AccountsPresenterActions
    ): BanksListScreenUIModel
}

class BanksListScreenMapperImpl(
    private val bankSectionMapper: BankSectionMapper,
) : BanksListScreenMapper {
    override suspend fun mapToUIModel(
        banksList: BanksList,
        accountsPresenterActions: AccountsPresenterActions
    ): BanksListScreenUIModel {
        return BanksListScreenUIModel(
            CABankSection =
                bankSectionMapper.toUIModel(
                    title = getString(Res.string.Accounts_List_Screen_CA_Section_Title),
                    banksList.CABanks,
                    accountsPresenterActions
                ),
            otherBanksSection =
                bankSectionMapper.toUIModel(
                    getString(Res.string.Accounts_List_Screen_Other_Banks_Section_Title),
                    banksList.otherBanks,
                    accountsPresenterActions
                )
        )
    }

}