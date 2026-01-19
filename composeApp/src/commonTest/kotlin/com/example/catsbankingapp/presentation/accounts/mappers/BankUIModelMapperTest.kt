package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.domain.models.Bank
import com.example.catsbankingapp.presentation.accounts.FakeAccountsPresenterActions
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter
import com.example.catsbankingapp.utils.currencyformatter.FakeCurrencyFormatter
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BankUIModelMapperTest : KoinTest {

    private val mapper: BankUIModelMapper by inject()
    private val fakeActions = FakeAccountsPresenterActions()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                singleOf(::FakeCurrencyFormatter) { bind<CurrencyFormatter>() }
                singleOf(::AccountsUIModelMapperImpl) { bind<AccountsUIModelMapper>() }
                singleOf(::BankUIModelMapperImpl) { bind<BankUIModelMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun toUIModel_maps_bank_correctly() {
        // Arrange
        val account = Account(id = "1", balance = 100.0)
        val bank = Bank(
            name = "My Bank",
            isCA = true,
            accounts = listOf(account)
        )

        // Act
        val result = mapper.toUIModel(bank, fakeActions)

        // Assert
        assertEquals("My Bank", result.title)
        assertEquals(1, result.accounts.size)
        // Check total balance mapping (100.0 formatted)
        assertEquals("100.0 Formatted", result.totalAccountsBalances)
    }

    @Test
    fun toUIModelList_maps_list_correctly() {
        // Arrange
        val bank = Bank(name = "Bank 1", accounts = emptyList())
        val list = listOf(bank)

        // Act
        val result = mapper.toUIModelList(list, fakeActions)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Bank 1", result.first().title)
    }
}
