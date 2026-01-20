package com.example.catsbankingapp.presentation.operations

import com.example.catsbankingapp.domain.GetAccountOperationsListUseCase
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapper
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class OperationsListUIState() {
    object Loading : OperationsListUIState()
    data class Success(val accountOperationsScreenModel: AccountOperationsScreenModel) : OperationsListUIState()
    data class Error(val message: String, val onRetry: () -> Unit = {}) : OperationsListUIState()
}

sealed class OperationsListEvents() {
    object OnRetryClicked : OperationsListEvents()
}

interface OperationsListPresenter {
    val uiState: Flow<OperationsListUIState>

    val events: Flow<OperationsListEvents>

    fun getAccountOperationsList(accountId: String)
}

class OperationsListPresenterImpl(
    private val coroutineScope: CoroutineScope,
    private val getAccountOperationsListUseCase: GetAccountOperationsListUseCase,
    private val accountOperationsScreenModelMapper: AccountOperationsScreenModelMapper
) : OperationsListPresenter {
    private val _uiState = MutableStateFlow<OperationsListUIState>(OperationsListUIState.Loading)
    override val uiState: StateFlow<OperationsListUIState> = _uiState

    private val _events = Channel<OperationsListEvents>(
        capacity = Channel.BUFFERED
    )
    override val events: Flow<OperationsListEvents> = _events.receiveAsFlow()

    override fun getAccountOperationsList(accountId: String) {
        coroutineScope.launch {
            getAccountOperationsListUseCase.getAccountOperationsList(accountId).collect { result ->
                result.onSuccess {
                    _uiState.value = OperationsListUIState.Success(
                        accountOperationsScreenModelMapper.toUIModel(it)
                    )
                }.onFailure {
                    _uiState.value = OperationsListUIState.Error(
                        it.message.orEmpty(),
                        onRetry = { onRetryClicked() }
                    )
                }
            }
        }
    }

    private fun onRetryClicked() {
        coroutineScope.launch {
            _events.send(OperationsListEvents.OnRetryClicked)
        }
    }
}