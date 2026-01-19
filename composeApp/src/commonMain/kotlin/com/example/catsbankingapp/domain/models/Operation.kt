package com.example.catsbankingapp.domain.models

import kotlinx.datetime.LocalDate

data class Operation(
    val id: String? = null,
    val title: String? = null,
    val amount: Double,
    val category: String? = null,
    val date: LocalDate? = null
)