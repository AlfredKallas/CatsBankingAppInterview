package com.example.catsbankingapp.presentation.accounts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAccountsPresenter : AccountsPresenter, AccountsPresenterActions {
    
    // Public mutable state for test manipulation
    val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading("Test"))
    override val uiState: Flow<BanksListScreenUIState> = _uiState

    private val _events = MutableSharedFlow<AccountsEvents>(replay = 1)
    override val events: Flow<AccountsEvents> = _events

    var getBanksUIListCalled = false

    override fun getBanksUIList() {
        getBanksUIListCalled = true
    }

    override fun onAccountClicked(accountId: String) {
        _events.tryEmit(AccountsEvents.OnAccountClicked(accountId))
    }

    override fun onRetryClicked() {
        getBanksUIListCalled = true // Similar to real presenter logic
    }
}

class FakeAccountsPresenterFactory : AccountsPresenterFactory {
    val fakePresenter = FakeAccountsPresenter()
    override fun create(coroutineScope: CoroutineScope): AccountsPresenter {
        return fakePresenter
    }
}
