package com.example.catsbankingapp.data

import com.example.catsbankingapp.core.local.LocalBanksListDataSource
import com.example.catsbankingapp.data.models.BankModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalBanksListDataSource : LocalBanksListDataSource {
    var localBanks: List<BankModel>? = null

    override suspend fun getBanksList(): Flow<Result<List<BankModel>>> = flow {
        if (localBanks != null) {
            emit(Result.success(localBanks!!))
        } else {
            emit(Result.failure(Exception("No local data")))
        }
    }

    override fun saveBanksList(banksList: List<BankModel>) {
        localBanks = banksList
    }
}
