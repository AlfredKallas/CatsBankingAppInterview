package com.example.catsbankingapp.presentation.accounts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FakeAccountsPresenter(
    private val coroutineScope: CoroutineScope
) : AccountsPresenter, AccountsPresenterActions {
    
    // Public mutable state for test manipulation
    val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading(""))
    override val uiState: Flow<BanksListScreenUIState> = _uiState

    private val _events = MutableSharedFlow<AccountsEvents>(extraBufferCapacity = 1)
    override val events: Flow<AccountsEvents> = _events

    var getBanksUIListCalled = false

    override fun getBanksUIList() {
        getBanksUIListCalled = true
    }

    override fun onAccountClicked(accountId: String) {
        coroutineScope.launch {
            _events.emit(AccountsEvents.OnAccountClicked(accountId))
        }
    }

    override fun onRetryClicked() {
        getBanksUIListCalled = true // Similar to real presenter logic
    }
}

class FakeAccountsPresenterFactory : AccountsPresenterFactory {
    lateinit var fakePresenter: FakeAccountsPresenter
    override fun create(coroutineScope: CoroutineScope): AccountsPresenter {
        val fakePresenter = FakeAccountsPresenter(coroutineScope)
        this.fakePresenter = fakePresenter
        return fakePresenter
    }
}
