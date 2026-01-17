package com.example.catsbankingapp.presentation.mainScreen

import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.presentation.mainScreen.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.mainScreen.models.BanksListScreenUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class BanksListScreenUIState {
    object Loading : BanksListScreenUIState()
    data class Success(val banksList: BanksListScreenUIModel) : BanksListScreenUIState()
    data class Error(val message: String) : BanksListScreenUIState()
}

interface MainScreenPresenter {

    val uiState: StateFlow<BanksListScreenUIState>

    suspend fun getBanksUIList()
}

class MainScreenPresenterImpl(
    private val getBanksListUseCase: GetBanksListUseCase,
    private val banksListScreenMapper: BanksListScreenMapper
): MainScreenPresenter {

    private val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading)
    override val uiState: StateFlow<BanksListScreenUIState> = _uiState.asStateFlow()

    override suspend fun getBanksUIList() =
        getBanksListUseCase.getBanksList().collect { result ->
            result.onSuccess {
                _uiState.value = BanksListScreenUIState.Success(banksListScreenMapper.mapToUIModel(it))
            }.onFailure {
                _uiState.value = BanksListScreenUIState.Error(it.message.orEmpty())
            }
        }
}