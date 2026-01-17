package com.example.catsbankingapp.presentation.accounts

import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class BanksListScreenUIState(val screenTitle: String) {
    data class Loading(val title: String) : BanksListScreenUIState(title)
    data class Success(val title: String, val banksList: BanksListScreenUIModel) : BanksListScreenUIState(title)
    data class Error(val title: String, val message: String) : BanksListScreenUIState(title)
}

interface MainScreenPresenter {

    val uiState: StateFlow<BanksListScreenUIState>

    suspend fun getBanksUIList()
}

class MainScreenPresenterImpl(
    private val getBanksListUseCase: GetBanksListUseCase,
    private val banksListScreenMapper: BanksListScreenMapper
): MainScreenPresenter {

    private val _uiState = MutableStateFlow<BanksListScreenUIState>(BanksListScreenUIState.Loading("Mes Comptes"))
    override val uiState: StateFlow<BanksListScreenUIState> = _uiState.asStateFlow()

    override suspend fun getBanksUIList() =
        getBanksListUseCase.getBanksList().collect { result ->
            result.onSuccess {
                _uiState.value = BanksListScreenUIState.Success("mes Comptes", banksListScreenMapper.mapToUIModel(it))
            }.onFailure {
                _uiState.value = BanksListScreenUIState.Error("mes Comptes", it.message.orEmpty())
            }
        }
}