package com.example.catsbankingapp.presentation.operations

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeOperationsListPresenter : OperationsListPresenter {
    
    val _uiState = MutableStateFlow<OperationsListUIState>(OperationsListUIState.Loading)
    override val uiState: StateFlow<OperationsListUIState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<OperationsListEvents>()
    override val events: SharedFlow<OperationsListEvents> = _events
    
    var lastAccountIdRequested: String? = null

    override suspend fun getAccountOperationsList(accountId: String) {
        lastAccountIdRequested = accountId
    }
}
