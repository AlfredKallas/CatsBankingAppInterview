package com.example.catsbankingapp.presentation.operations.models

data class AccountOperationsScreenModel(
    val accountTitle: String,
    val totalBalance: String,
    val operations: List<OperationUIModel>
)