package com.example.catsbankingapp.presentation.accounts.models

data class BankUIModel(
    val title: String,
    val accounts: List<AccountUIModel>,
    val totalAccountsBalances: String
)
