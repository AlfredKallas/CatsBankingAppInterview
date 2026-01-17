package com.example.catsbankingapp.presentation.operations.mappers

import com.example.catsbankingapp.domain.models.Operation
import com.example.catsbankingapp.presentation.operations.models.OperationUIModel
import com.example.catsbankingapp.presentation.operations.models.toUIModel
import com.example.catsbankingapp.presentation.operations.models.toUIModelList
import com.example.catsbankingapp.utils.DateTimeFormatter

interface OperationUiModelMapper {
    fun toUIModel(operation: Operation): OperationUIModel

    fun toUIModelList(operations: List<Operation>): List<OperationUIModel>
}

class OperationUiModelMapperImpl(
    private val dateTimeFormatter: DateTimeFormatter
): OperationUiModelMapper {
    override fun toUIModel(operation: Operation): OperationUIModel =
        operation.toUIModel(dateTimeFormatter)

    override fun toUIModelList(operations: List<Operation>): List<OperationUIModel> =
        operations.toUIModelList(dateTimeFormatter)

}