package com.example.catsbankingapp.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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

    val uiState = presenter.uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BanksListScreenUIState.Loading("")
    )

    val events = presenter.events
}