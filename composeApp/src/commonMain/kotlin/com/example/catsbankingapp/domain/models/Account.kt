package com.example.catsbankingapp.domain.models

import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.utils.DateTimeParser

data class Account(
    val order: Int? = null,
    val id: String? = null,
    val holder: String? = null,
    val role: Int? = null,
    val contractNumber: String? = null,
    val label: String? = null,
    val productCode: String? = null,
    val balance: Double? = null,
    val operations: List<Operation> = listOf()
)

fun AccountModel.toAccount(dateTimeParser: DateTimeParser) = Account(
    order = this.order,
    id = this.id,
    holder = this.holder,
    role = this.role,
    contractNumber = this.contractNumber,
    label = this.label,
    productCode = this.productCode,
    balance = this.balance,
    operations = this.operations.toOperationList(dateTimeParser)
)

fun List<AccountModel>.toAccountList(dateTimeParser: DateTimeParser) =
    this.map { it.toAccount(dateTimeParser) }