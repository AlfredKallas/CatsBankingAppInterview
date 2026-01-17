package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Bank
import com.example.catsbankingapp.presentation.accounts.models.BankSectionUIModel

interface BankSectionMapper {
    fun toUIModel(title: String, banks: List<Bank>): BankSectionUIModel
}

class BankSectionMapperImpl(private val bankUIStateMapper: BankUIStateMapper) : BankSectionMapper {
    override fun toUIModel(title: String, banks: List<Bank>): BankSectionUIModel {
        return BankSectionUIModel(
            title = title,
            banks = bankUIStateMapper.toUIModelList(banks)
        )
    }

}