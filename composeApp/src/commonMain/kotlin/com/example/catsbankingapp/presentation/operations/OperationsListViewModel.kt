package com.example.catsbankingapp.presentation.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import com.example.catsbankingapp.presentation.navigation.OperationsForAccount
import com.example.catsbankingapp.utils.NavArgsProvider
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class OperationsListViewModel(
    private val operationsListPresenter: OperationsListPresenter,
    navArgsProvider: NavArgsProvider
): ViewModel() {

    private val navArgs: OperationsForAccount = navArgsProvider.provideArg(OperationsForAccount::class,
        typeMap = mapOf(
            typeOf<String>() to NavType.StringType
        ))

    val uiState = operationsListPresenter.uiState.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = OperationsListUIState.Loading
    )

    val events = operationsListPresenter.events

    init {
        getAccountOperationsList()
    }

    fun getAccountOperationsList(){
        viewModelScope.launch {
            operationsListPresenter.getAccountOperationsList(navArgs.accountId)
        }
    }
}