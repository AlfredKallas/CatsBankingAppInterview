package com.example.catsbankingapp.presentation.accounts.mappers

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

class BankSectionUIStateMapperTest : KoinTest {

    private val mapper: BankSectionMapper by inject()
    private val fakeActions = FakeAccountsPresenterActions()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                singleOf(::FakeCurrencyFormatter) { bind<CurrencyFormatter>() }
                singleOf(::AccountsUIModelMapperImpl) { bind<AccountsUIModelMapper>() }
                singleOf(::BankUIModelMapperImpl) { bind<BankUIModelMapper>() }
                singleOf(::BankSectionMapperImpl) { bind<BankSectionMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `toUIModel maps section correctly`() {
        // Arrange
        val bank = Bank(name = "Bank 1", accounts = emptyList())
        val list = listOf(bank)

        // Act
        val result = mapper.toUIModel("My Section", list, fakeActions)

        // Assert
        assertEquals("My Section", result.title)
        assertEquals(1, result.banks.size)
        assertEquals("Bank 1", result.banks.first().title)
    }
}
