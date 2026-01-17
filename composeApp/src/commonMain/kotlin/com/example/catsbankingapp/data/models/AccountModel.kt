package com.example.catsbankingapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountModel(
    @SerialName("order") val order: Int? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("holder") val holder: String? = null,
    @SerialName("role") val role: Int? = null,
    @SerialName("contract_number") val contractNumber: String? = null,
    @SerialName("label") val label: String? = null,
    @SerialName("product_code") val productCode: String? = null,
    @SerialName("balance") val balance: Double? = null,
    @SerialName("operations") val operations: ArrayList<OperationModel> = arrayListOf()
)