package com.example.catsbankingapp.domain.mappers

import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.domain.models.Bank

interface BankMapper {
    fun toBank(bankModel: BankModel): Bank
    fun toBankList(bankModelList: List<BankModel>): List<Bank>
}

class BankMapperImpl(
    private val accountMapper: AccountMapper
) : BankMapper {
    override fun toBank(bankModel: BankModel): Bank = Bank(
        name = bankModel.name,
        isCA = bankModel.isCA,
        accounts = accountMapper.toAccountList(bankModel.accounts)
    )

    override fun toBankList(bankModelList: List<BankModel>): List<Bank> =
        bankModelList.map { toBank(it) }
}