package com.example.catsbankingapp.data

import com.example.catsbankingapp.core.local.LocalBanksListDataSource
import com.example.catsbankingapp.core.network.NetworkClient
import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.data.serviceType.BankAccountsService
import com.example.catsbankingapp.utils.mapOnSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf

interface BanksListRepository {
    suspend fun getBanksList(): Flow<Result<List<BankModel>>>
}

class BanksListRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localBanksListDataSource: LocalBanksListDataSource
): BanksListRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getBanksList(): Flow<Result<List<BankModel>>> {
        return localBanksListDataSource.getBanksList().flatMapLatest { result ->
            if (result.isSuccess) {
                flowOf(result)
            } else {
                networkClient.performNetworkCall(BankAccountsService()).mapOnSuccess {
                    localBanksListDataSource.saveBanksList(it)
                    it
                }
            }
        }
    }
}