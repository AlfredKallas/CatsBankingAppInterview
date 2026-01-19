package com.example.catsbankingapp.presentation.accounts

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAccountsPresenter : AccountsPresenter {
    
    // Public mutable state for test manipulation
    val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading("Test"))
    override val uiState: StateFlow<BanksListScreenUIState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AccountsEvents>()
    override val events: SharedFlow<AccountsEvents> = _events

    var getBanksUIListCalled = false

    override suspend fun getBanksUIList() {
        getBanksUIListCalled = true
    }
}
