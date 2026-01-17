package com.example.catsbankingapp.presentation.accounts.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountUIModel(
    val title: String,
    val accountBalance: String,
    val onClick: (String) -> Unit
)