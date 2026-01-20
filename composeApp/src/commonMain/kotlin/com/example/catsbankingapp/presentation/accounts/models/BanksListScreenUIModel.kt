package com.example.catsbankingapp.presentation.accounts.models

import androidx.compose.runtime.Immutable

@Immutable
data class BanksListScreenUIModel(
    val CABankSection: BankSectionUIModel,
    val otherBanksSection: BankSectionUIModel
)