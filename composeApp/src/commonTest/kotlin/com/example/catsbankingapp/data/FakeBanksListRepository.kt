package com.example.catsbankingapp.data

import com.example.catsbankingapp.data.models.BankModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBanksListRepository : BanksListRepository {
    
    var banksListResult: Result<List<BankModel>>? = null

    override fun getBanksList(): Flow<Result<List<BankModel>>> = flow {
        if (banksListResult != null) {
            emit(banksListResult!!)
        } else {
            // Default empty success if not set
            emit(Result.success(emptyList()))
        }
    }
}
