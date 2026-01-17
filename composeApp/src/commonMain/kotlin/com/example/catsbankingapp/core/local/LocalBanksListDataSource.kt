package com.example.catsbankingapp.core.local

import com.example.catsbankingapp.core.CatsBankingException
import com.example.catsbankingapp.data.models.BankModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

interface LocalBanksListDataSource {
    suspend fun getBanksList(): Flow<Result<List<BankModel>>>
    fun saveBanksList(banksList: List<BankModel>)
}

class LocalBanksListDataSourceImpl : LocalBanksListDataSource {
    private var localBanksList :List<BankModel> = emptyList()
    override suspend fun getBanksList(): Flow<Result<List<BankModel>>> {
        return flow {
            if (localBanksList.isNotEmpty()) {
                emit(Result.success(localBanksList))
            } else {
                val exception =
                    CatsBankingException.NoLocalStorageException("No local data found")
                emit(Result.failure(exception))
            }
        }
    }

    override fun saveBanksList(banksList: List<BankModel>) {
        this.localBanksList = banksList
    }
}