package com.example.catsbankingapp.presentation.accounts

import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class BanksListScreenUIState(val screenTitle: String) {
    data class Loading(val title: String) : BanksListScreenUIState(title)
    data class Success(val title: String, val banksList: BanksListScreenUIModel) : BanksListScreenUIState(title)
    data class Error(val title: String, val message: String) : BanksListScreenUIState(title)
}

interface AccountsPresenter {

    val uiState: StateFlow<BanksListScreenUIState>

    val events: SharedFlow<AccountsEvents>

    suspend fun getBanksUIList()
}

interface AccountsPresenterActions {
    fun onAccountClicked(accountId: String)
}

sealed class AccountsEvents {
    data class OnAccountClicked(val accountId: String) : AccountsEvents()
}

class AccountsPresenterImpl(
    private val getBanksListUseCase: GetBanksListUseCase,
    private val banksListScreenMapper: BanksListScreenMapper
): AccountsPresenter, AccountsPresenterActions {

    private val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading("Mes Comptes"))
    override val uiState: StateFlow<BanksListScreenUIState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AccountsEvents>()
    override val events: SharedFlow<AccountsEvents> = _events

    override suspend fun getBanksUIList() =
        getBanksListUseCase.getBanksList().collect { result ->
            result.onSuccess { banksList ->
                _uiState.value = BanksListScreenUIState.Success(
                    "mes Comptes",
                    banksListScreenMapper.mapToUIModel(banksList, this@AccountsPresenterImpl)
                )
            }.onFailure {
                _uiState.value = BanksListScreenUIState.Error("mes Comptes", it.message.orEmpty())
            }
        }

    override fun onAccountClicked(accountId: String) {
        _events.tryEmit(AccountsEvents.OnAccountClicked(accountId))
    }
}