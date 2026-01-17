package com.example.catsbankingapp.presentation.operations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.catsbankingapp.presentation.navigation.OperationsForAccount
import kotlinx.coroutines.launch

class OperationsListViewModel(
    savedStateHandle: SavedStateHandle,
    private val operationsListPresenter: OperationsListPresenter,
): ViewModel() {

    private val accountID: String =
        savedStateHandle.toRoute<OperationsForAccount>().accountId

    val uiState = operationsListPresenter.uiState

    init {
        viewModelScope.launch {
            operationsListPresenter.getAccountOperationsList(accountID)
        }

    }
}