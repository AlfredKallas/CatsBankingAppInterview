package com.example.catsbankingapp.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catsbankingapp.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AccountsViewModel(
    presenterFactory: AccountsPresenterFactory
) : ViewModel() {

    private val presenter = presenterFactory.create(viewModelScope)

    fun getBanksList() {
        viewModelScope.launch {
            presenter.getBanksUIList()
        }
    }

    val uiState = presenter.uiState.onStart {
        getBanksList()
    }.stateInWhileSubscribed(
        initialValue = BanksListScreenUIState.Loading("")
    )

    val events = presenter.events
}