package com.example.catsbankingapp.presentation.accounts.models

import androidx.compose.runtime.Immutable

@Immutable
data class BankUIModel(
    val title: String,
    val accounts: List<AccountUIModel>,
    val totalAccountsBalances: String
)
