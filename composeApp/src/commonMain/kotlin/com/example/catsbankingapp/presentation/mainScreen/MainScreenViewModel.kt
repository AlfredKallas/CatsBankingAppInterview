package com.example.catsbankingapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainScreenViewModel(presenter: MainScreenPresenter) : ViewModel() {
    init {
        viewModelScope.launch {
            presenter.getBanksUIList()
        }
    }

    val uiState = presenter.uiState
}