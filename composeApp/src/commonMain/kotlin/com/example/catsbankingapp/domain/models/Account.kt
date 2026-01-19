package com.example.catsbankingapp.domain.models

data class Account(
    val id: String? = null,
    val label: String? = null,
    val balance: Double,
    val operations: List<Operation>? = null
)