package com.example.catsbankingapp.presentation.accounts

import catsbankingapp.composeapp.generated.resources.Accounts_List_Screen_Title
import catsbankingapp.composeapp.generated.resources.Res
import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import com.example.catsbankingapp.utils.DispatchersProvider
import com.example.catsbankingapp.utils.StringProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class BanksListScreenUIState(val screenTitle: String) {
    data class Loading(val title: String) : BanksListScreenUIState(title)
    data class Success(val title: String, val banksList: BanksListScreenUIModel) : BanksListScreenUIState(title)
    data class Error(val title: String, val message: String, val onRetry:() -> Unit = {}) : BanksListScreenUIState(title)
}

interface AccountsPresenter {

    val uiState: Flow<BanksListScreenUIState>

    val events: Flow<AccountsEvents>

    fun getBanksUIList()
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
    private val coroutineScope: CoroutineScope,
    private val dispatchersProvider: DispatchersProvider,
    private val getBanksListUseCase: GetBanksListUseCase,
    private val banksListScreenMapper: BanksListScreenMapper,
    private val stringProvider: StringProvider
): AccountsPresenter, AccountsPresenterActions {

    private val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading(""))
    override val uiState: Flow<BanksListScreenUIState> = _uiState

    private val _events = Channel<AccountsEvents>(
        capacity = Channel.BUFFERED
    )
    override val events: Flow<AccountsEvents> = _events.receiveAsFlow()

    override fun getBanksUIList() {
        coroutineScope.launch {
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
    }

    override fun onRetryClicked() {
        coroutineScope.launch(dispatchersProvider.mainImmediate) {
            _events.send(AccountsEvents.OnRetryClicked)
        }
    }
    override fun onAccountClicked(accountId: String) {
        coroutineScope.launch(dispatchersProvider.mainImmediate) {
            _events.send(AccountsEvents.OnAccountClicked(accountId))
        }
    }
}