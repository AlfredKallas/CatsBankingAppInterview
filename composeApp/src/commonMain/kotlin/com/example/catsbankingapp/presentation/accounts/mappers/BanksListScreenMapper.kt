package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.BanksList
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel

interface BanksListScreenMapper {
    fun mapToUIModel(banksList: BanksList): BanksListScreenUIModel
}

class BanksListScreenMapperImpl(private val bankSectionMapper: BankSectionMapper) : BanksListScreenMapper {
    override fun mapToUIModel(banksList: BanksList): BanksListScreenUIModel {
        return BanksListScreenUIModel(
            CABankSection = bankSectionMapper.toUIModel("CABanks", banksList.CABanks), //TODO: Change title to resources afterwards
            otherBanksSection = bankSectionMapper.toUIModel("Other Banks", banksList.otherBanks) //TODO: Change title to resources afterwards
        )
    }

}