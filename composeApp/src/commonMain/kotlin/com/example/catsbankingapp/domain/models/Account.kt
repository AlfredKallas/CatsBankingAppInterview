package com.example.catsbankingapp.domain.models

import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.utils.DateTimeParser

data class Account(
    val id: String? = null,
    val label: String? = null,
    val balance: Double? = null,
    val operations: List<Operation> = listOf()
)

fun AccountModel.toAccount(dateTimeParser: DateTimeParser? = null) = Account(
    id = this.id,
    label = this.label,
    balance = this.balance,
    operations = this.operations.toOperationList(dateTimeParser)
)

fun AccountModel.toAccountWithAdditionalSorting(dateTimeParser: DateTimeParser, comparator: Comparator<Operation>) = Account(
    id = this.id,
    label = this.label,
    balance = this.balance,
    operations = this.operations.toOperationList(dateTimeParser).sortedWith(comparator)
)

fun List<AccountModel>.toAccountList(dateTimeParser: DateTimeParser? = null) =
    this.map { it.toAccount(dateTimeParser) }