package com.example.catsbankingapp.presentation.operations.models

data class OperationUIModel(
    val id: String,
    val title: String,
    val date: String?,
    val balance: String
)