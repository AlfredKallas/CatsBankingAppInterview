package com.example.catsbankingapp.domain.models

import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.utils.DateTimeParser

data class Bank(
    val name: String? = null,
    val isCA: Boolean = false,
    val accounts: List<Account> = listOf()
)

fun BankModel.toBank(dateTimeParser: DateTimeParser? = null) = Bank(
    name = this.name,
    isCA = this.isCA,
    accounts = this.accounts.toAccountList(dateTimeParser)
)

fun List<BankModel>.toBankList(dateTimeParser: DateTimeParser? = null) =
    this.map { it.toBank(dateTimeParser) }