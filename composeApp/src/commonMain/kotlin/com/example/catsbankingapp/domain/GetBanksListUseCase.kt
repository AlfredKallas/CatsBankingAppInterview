package com.example.catsbankingapp.domain

import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.domain.models.BanksList
import com.example.catsbankingapp.domain.models.toBankList
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.mapOnSuccess
import kotlinx.coroutines.flow.Flow

class GetBanksListUseCase(
    private val banksListRepository: BanksListRepository,
    private val dateTimeParser: DateTimeParser
) {
    suspend fun getBanksList(): Flow<Result<BanksList>> {
        return banksListRepository.getBanksList()
            .mapOnSuccess { banks ->
                val (caBanks, otherBanks) = banks.partition { it.isCA }
                val sortedCABanks = caBanks.sortedBy { it.name }
                val sortedOtherBanks = otherBanks.sortedBy { it.name }
                BanksList(
                    CABanks = sortedCABanks.toBankList(dateTimeParser),
                    otherBanks = sortedOtherBanks.toBankList(dateTimeParser)
                )
            }
    }
}