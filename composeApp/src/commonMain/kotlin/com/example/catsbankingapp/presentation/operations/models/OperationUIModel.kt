package com.example.catsbankingapp.presentation.operations.models

import com.example.catsbankingapp.domain.models.Operation
import com.example.catsbankingapp.utils.DateTimeFormatter

data class OperationUIModel(
    val title: String,
    val date: String?,
    val balance: String
)

fun Operation.toUIModel(dateTimeFormatter: DateTimeFormatter) = OperationUIModel(
    title = this.title.orEmpty(),
    date = this.date?.let { dateTimeFormatter.format(it) },
    balance = amount.toString()
)

fun List<Operation>.toUIModelList(dateTimeFormatter: DateTimeFormatter) =
    this.map { it.toUIModel(dateTimeFormatter) }