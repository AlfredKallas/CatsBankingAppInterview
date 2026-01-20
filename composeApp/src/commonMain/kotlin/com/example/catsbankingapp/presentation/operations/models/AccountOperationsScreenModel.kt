package com.example.catsbankingapp.presentation.operations.models

import androidx.compose.runtime.Immutable

@Immutable
data class AccountOperationsScreenModel(
    val accountTitle: String,
    val totalBalance: String,
    val operations: List<OperationUIModel>
)