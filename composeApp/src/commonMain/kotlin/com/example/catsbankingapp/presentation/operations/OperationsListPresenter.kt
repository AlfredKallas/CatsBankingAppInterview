package com.example.catsbankingapp.presentation.operations

import com.example.catsbankingapp.domain.GetAccountOperationsListUseCase
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapper
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

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

    val events: SharedFlow<OperationsListEvents>

    suspend fun getAccountOperationsList(accountId: String)
}

class OperationsListPresenterImpl(
    private val getAccountOperationsListUseCase: GetAccountOperationsListUseCase,
    private val accountOperationsScreenModelMapper: AccountOperationsScreenModelMapper
) : OperationsListPresenter {
    private val _uiState = MutableStateFlow<OperationsListUIState>(OperationsListUIState.Loading)
    override val uiState: StateFlow<OperationsListUIState> = _uiState

    private val _events = MutableSharedFlow<OperationsListEvents>(
        replay = 1,
    )
    override val events: SharedFlow<OperationsListEvents> = _events

    override suspend fun getAccountOperationsList(accountId: String) {
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

    private fun onRetryClicked() {
        _events.tryEmit(OperationsListEvents.OnRetryClicked)
    }
}