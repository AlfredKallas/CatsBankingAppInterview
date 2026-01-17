package com.example.catsbankingapp.presentation.mainScreen.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountUIModel(
    val title: String,
    val accountBalance: String,
    val onClick: () -> Unit
)