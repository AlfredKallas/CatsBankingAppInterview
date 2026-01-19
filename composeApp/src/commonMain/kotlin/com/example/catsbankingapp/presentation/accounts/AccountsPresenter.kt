package com.example.catsbankingapp.presentation.accounts

import catsbankingapp.composeapp.generated.resources.Accounts_List_Screen_Title
import catsbankingapp.composeapp.generated.resources.Res
import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import com.example.catsbankingapp.utils.StringProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

sealed class BanksListScreenUIState(val screenTitle: String) {
    data class Loading(val title: String) : BanksListScreenUIState(title)
    data class Success(val title: String, val banksList: BanksListScreenUIModel) : BanksListScreenUIState(title)
    data class Error(val title: String, val message: String, val onRetry:() -> Unit = {}) : BanksListScreenUIState(title)
}

interface AccountsPresenter {

    val uiState: Flow<BanksListScreenUIState>

    val events: SharedFlow<AccountsEvents>

    suspend fun getBanksUIList()
}

interface AccountsPresenterActions {
    fun onAccountClicked(accountId: String)
    fun onRetryClicked()
}

sealed class AccountsEvents {
    data class OnAccountClicked(val accountId: String) : AccountsEvents()
    object OnRetryClicked : AccountsEvents()
}

class AccountsPresenterImpl(
    private val getBanksListUseCase: GetBanksListUseCase,
    private val banksListScreenMapper: BanksListScreenMapper,
    private val stringProvider: StringProvider
): AccountsPresenter, AccountsPresenterActions {

    private val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading(""))
    override val uiState: Flow<BanksListScreenUIState> = _uiState

    private val _events = MutableSharedFlow<AccountsEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    override val events: SharedFlow<AccountsEvents> = _events

    override suspend fun getBanksUIList() {
        val title = stringProvider.getString(Res.string.Accounts_List_Screen_Title)
        _uiState.value = BanksListScreenUIState.Loading(title)
        getBanksListUseCase.getBanksList().collect { result ->
            result.onSuccess { banksList ->
                _uiState.value = BanksListScreenUIState.Success(
                    title = title,
                    banksList = banksListScreenMapper.mapToUIModel(
                        banksList,
                        this@AccountsPresenterImpl
                    )
                )
            }.onFailure {
                _uiState.value = BanksListScreenUIState.Error(
                    title = title,
                    message = it.message.orEmpty(),
                    onRetry = { onRetryClicked() }
                )
            }
        }
    }

    override fun onRetryClicked() {
        _events.tryEmit(AccountsEvents.OnRetryClicked)
    }
    override fun onAccountClicked(accountId: String) {
        _events.tryEmit(AccountsEvents.OnAccountClicked(accountId))
    }
}