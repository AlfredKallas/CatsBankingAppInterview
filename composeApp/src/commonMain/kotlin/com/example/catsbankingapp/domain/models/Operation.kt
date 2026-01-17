package com.example.catsbankingapp.domain.models

import com.example.catsbankingapp.data.models.OperationModel
import com.example.catsbankingapp.utils.DateTimeParser
import kotlinx.datetime.LocalDateTime


data class Operation(
    val id: String? = null,
    val title: String? = null,
    val amount: String? = null,
    val category: String? = null,
    val date: LocalDateTime? = null
)

fun OperationModel.toOperation(dateTimeParser: DateTimeParser? = null) = Operation(
    id = this.id,
    title = this.title,
    amount = this.amount,
    category = this.category,
    date = this.date?.toLong()?.let { dateTimeParser?.parse(it) }
)

fun List<OperationModel>.toOperationList(dateTimeParser: DateTimeParser? = null) =
    this.map { it.toOperation(dateTimeParser) }