package com.example.catsbankingapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OperationModel(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("amount") val amount: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("date") val date: String? = null
)