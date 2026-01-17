package com.example.catsbankingapp.presentation.mainScreen.models

data class BankUIModel(
    val title: String,
    val accounts: List<AccountUIModel>,
    val totalAccountsBalances: String
)
