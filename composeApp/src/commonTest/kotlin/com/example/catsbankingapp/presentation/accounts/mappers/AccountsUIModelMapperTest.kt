package com.example.catsbankingapp.presentation.accounts.mappers

import com.example.catsbankingapp.domain.models.Account
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

class AccountsUIModelMapperTest : KoinTest {

    private val mapper: AccountsUIModelMapper by inject()
    private val fakeActions = FakeAccountsPresenterActions()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                singleOf(::FakeCurrencyFormatter) { bind<CurrencyFormatter>() }
                singleOf(::AccountsUIModelMapperImpl) { bind<AccountsUIModelMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `toUIModel maps account correctly and handles click`() {
        // Arrange
        val account = Account(
            id = "1",
            label = "My Account",
            balance = 100.0,
            operations = emptyList()
        )
        val list = listOf(account)

        // Act
        val result = mapper.toUIModel(list, fakeActions)

        // Assert
        assertEquals(1, result.size)
        assertEquals("My Account", result.first().title)
        assertEquals("100.0 Formatted", result.first().accountBalance)

        // Verify click action
        result.first().onClick("")
        assertEquals("1", fakeActions.lastClickedAccountId)
    }
}
