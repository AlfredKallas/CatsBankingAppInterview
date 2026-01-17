package com.example.catsbankingapp.domain

import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.domain.models.Operation
import com.example.catsbankingapp.domain.models.toAccountWithAdditionalSorting
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.mapResultOnSuccess
import kotlinx.coroutines.flow.Flow

class GetAccountOperationsListUseCase(
    private val accountsRepository: BanksListRepository,
    private val dateTimeParser: DateTimeParser
) {
    suspend fun getAccountOperationsList(accountId: String): Flow<Result<Account>> {
        return accountsRepository.getBanksList().mapResultOnSuccess { bankModels ->
            val account = bankModels.flatMap { it.accounts }.find { it.id == accountId }
            if (account != null) {

                val compareByDate = compareBy<Operation> { it.date }.thenBy { it.title }
                val compareByDateAndName = compareByDate.thenBy { it.title }
                val domainAccount = account.toAccountWithAdditionalSorting(dateTimeParser, compareByDateAndName)
                Result.success(domainAccount)
            } else {
                Result.failure(Exception("Account not found"))
            }
        }
    }
}