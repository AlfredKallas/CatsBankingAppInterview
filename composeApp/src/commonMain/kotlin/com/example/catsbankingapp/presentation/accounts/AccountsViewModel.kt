package com.example.catsbankingapp.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AccountsViewModel(private val presenter: AccountsPresenter) : ViewModel() {

    init {
        getBanksList()
    }

    fun getBanksList() {
        viewModelScope.launch {
            presenter.getBanksUIList()
        }
    }

    val uiState = presenter.uiState

    val events = presenter.events
}