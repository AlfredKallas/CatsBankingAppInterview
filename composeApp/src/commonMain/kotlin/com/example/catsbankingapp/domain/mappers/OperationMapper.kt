package com.example.catsbankingapp.domain.mappers

import com.example.catsbankingapp.data.models.OperationModel
import com.example.catsbankingapp.domain.models.Operation
import com.example.catsbankingapp.domain.utils.formatAndConvertToDouble
import com.example.catsbankingapp.utils.DateTimeParser

interface OperationMapper {
    fun toOperation(operationModel: OperationModel): Operation
    fun toOperationList(operationModelList: List<OperationModel>, comparator: Comparator<Operation>? = null): List<Operation>
}

class OperationMapperImpl(
    private val dateTimeParser: DateTimeParser
) : OperationMapper {
    override fun toOperation(
        operationModel: OperationModel
    ): Operation = Operation(
        id = operationModel.id,
        title = operationModel.title,
        amount = operationModel.amount.formatAndConvertToDouble(),
        category = operationModel.category,
        date = operationModel.date?.toLong()?.let { dateTimeParser.parse(it) }
    )

    override fun toOperationList(
        operationModelList: List<OperationModel>,
        comparator: Comparator<Operation>?
    ): List<Operation> {
        val operations = operationModelList.map { toOperation(it) }
        return if (comparator != null) {
            operations.sortedWith(comparator)
        } else {
            operations
        }
    }
}