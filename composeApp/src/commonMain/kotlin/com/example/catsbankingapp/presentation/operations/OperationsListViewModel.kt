package com.example.catsbankingapp.presentation.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import com.example.catsbankingapp.presentation.navigation.OperationsForAccount
import com.example.catsbankingapp.utils.NavArgsProvider
import com.example.catsbankingapp.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class OperationsListViewModel(
    operationsListPresenterFactory: OperationsListPresenterFactory,
    navArgsProvider: NavArgsProvider
): ViewModel() {

    private val navArgs: OperationsForAccount = navArgsProvider.provideArg(OperationsForAccount::class,
        typeMap = mapOf(
            typeOf<String>() to NavType.StringType
        ))

    private val operationsListPresenter = operationsListPresenterFactory.create(viewModelScope)

    val uiState = operationsListPresenter.uiState.onStart {
        getAccountOperationsList()
    }.stateInWhileSubscribed(
        initialValue = OperationsListUIState.Loading
    )

    val events = operationsListPresenter.events

    fun getAccountOperationsList(){
        viewModelScope.launch {
            operationsListPresenter.getAccountOperationsList(navArgs.accountId)
        }
    }
}