package com.example.catsbankingapp.presentation.operations.mappers

import com.example.catsbankingapp.domain.models.Account
import com.example.catsbankingapp.utils.DateFormatter
import com.example.catsbankingapp.utils.DateFormatterImpl
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

class AccountOperationsScreenModelMapperTest : KoinTest {

    private val mapper: AccountOperationsScreenModelMapper by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                singleOf(::DateFormatterImpl) { bind<DateFormatter>() }
                singleOf(::FakeCurrencyFormatter) { bind<CurrencyFormatter>() }
                singleOf(::OperationUiModelMapperImpl) { bind<OperationUiModelMapper>() }
                singleOf(::AccountOperationsScreenModelMapperImpl) { bind<AccountOperationsScreenModelMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun toUIModel_maps_account_correctly() {
        // Arrange
        val account = Account(
            id = "1",
            label = "My Account",
            balance = 100.0,
            operations = emptyList()
        )

        // Act
        val result = mapper.toUIModel(account)

        // Assert
        assertEquals("My Account", result.accountTitle)
        assertEquals("100.0 Formatted", result.totalBalance)
        assertEquals(0, result.operations.size)
    }
}
