package com.example.catsbankingapp.presentation.operations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.example.catsbankingapp.presentation.navigation.OperationsForAccount
import com.example.catsbankingapp.utils.NavArgsProvider
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class OperationsListViewModel(
    private val operationsListPresenter: OperationsListPresenter,
    private val navArgsProvider: NavArgsProvider
): ViewModel() {

    private val navArgs: OperationsForAccount = navArgsProvider.provideArg(OperationsForAccount::class,
        typeMap = mapOf(
            typeOf<String>() to NavType.StringType
        ))

    val uiState = operationsListPresenter.uiState

    val events = operationsListPresenter.events

    init {
        getAccountOperationsList()
        println(" OperationsListViewModel -- init")
    }

    override fun onCleared() {
        super.onCleared()
        println(" OperationsListViewModel -- onCleared")
    }

    fun getAccountOperationsList(){
        viewModelScope.launch {
            operationsListPresenter.getAccountOperationsList(navArgs.accountId)
        }
    }
}