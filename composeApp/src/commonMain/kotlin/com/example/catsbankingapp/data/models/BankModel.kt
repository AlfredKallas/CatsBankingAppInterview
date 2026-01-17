package com.example.catsbankingapp.data.models

import com.example.catsbankingapp.data.serializer.BooleanAsIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankModel(
    @SerialName("name") val name: String? = null,
    @Serializable(with = BooleanAsIntSerializer::class)
    @SerialName("isCA") val isCA: Boolean = false,
    @SerialName("accounts") val accounts: ArrayList<AccountModel> = arrayListOf()
)