package com.example.catsbankingapp.data

import com.example.catsbankingapp.core.network.NetworkClient
import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.data.serviceType.BankAccountsService
import kotlinx.coroutines.flow.Flow

interface BanksListRepository {
    suspend fun getBanksList(): Flow<Result<List<BankModel>>>
}

class BanksListRepositoryImpl(
    private val networkClient: NetworkClient
): BanksListRepository {
    override suspend fun getBanksList(): Flow<Result<List<BankModel>>> {

        return networkClient.performNetworkCall(BankAccountsService())
    }

}