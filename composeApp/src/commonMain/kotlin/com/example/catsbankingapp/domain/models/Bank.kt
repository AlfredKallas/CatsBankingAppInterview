package com.example.catsbankingapp.domain.models

data class Bank(
    val name: String? = null,
    val isCA: Boolean = false,
    val accounts: List<Account> = listOf()
)