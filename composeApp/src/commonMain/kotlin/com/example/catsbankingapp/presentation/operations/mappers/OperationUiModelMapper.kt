package com.example.catsbankingapp.presentation.operations.mappers

import com.example.catsbankingapp.domain.models.Operation
import com.example.catsbankingapp.presentation.operations.models.OperationUIModel
import com.example.catsbankingapp.utils.DateFormatter
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter

interface OperationUiModelMapper {
    fun toUIModel(operation: Operation): OperationUIModel

    fun toUIModelList(operations: List<Operation>): List<OperationUIModel>
}

class OperationUiModelMapperImpl(
    private val dateFormatter: DateFormatter,
    private val currencyFormatter: CurrencyFormatter
): OperationUiModelMapper {
    override fun toUIModel(operation: Operation): OperationUIModel =
        OperationUIModel(
            id = operation.id.orEmpty(),
            title = operation.title.orEmpty(),
            date = operation.date?.let { dateFormatter.format(it) },
            balance = currencyFormatter.format(operation.amount)
        )

    override fun toUIModelList(operations: List<Operation>): List<OperationUIModel> =
        operations.map { toUIModel(it) }

}