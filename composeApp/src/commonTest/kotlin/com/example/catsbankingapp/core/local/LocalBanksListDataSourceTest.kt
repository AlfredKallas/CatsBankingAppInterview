package com.example.catsbankingapp.core.local

import com.example.catsbankingapp.core.CatsBankingException
import com.example.catsbankingapp.data.models.BankModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LocalBanksListDataSourceTest {

    private val dataSource = LocalBanksListDataSourceImpl()

    @Test
    fun getBanksList_returns_failure_when_cache_is_empty() = runTest {
        val result = dataSource.getBanksList().first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CatsBankingException.NoLocalStorageException)
    }

    @Test
    fun getBanksList_returns_success_after_save() = runTest {
        val banks = listOf(BankModel(name = "Test Bank", isCA = true, accounts = arrayListOf()))
        dataSource.saveBanksList(banks)

        val result = dataSource.getBanksList().first()

        assertTrue(result.isSuccess)
        assertEquals(banks, result.getOrNull())
    }
}
