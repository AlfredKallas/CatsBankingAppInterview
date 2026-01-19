package com.example.catsbankingapp.presentation.operations.mappers

import com.example.catsbankingapp.domain.models.Operation
import com.example.catsbankingapp.utils.DateFormatterImpl
import com.example.catsbankingapp.utils.DateFormatter
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter
import com.example.catsbankingapp.utils.currencyformatter.FakeCurrencyFormatter
import kotlinx.datetime.LocalDate
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

class OperationUiModelMapperTest : KoinTest {

    private val mapper: OperationUiModelMapper by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                singleOf(::DateFormatterImpl) { bind<DateFormatter>() }
                singleOf(::FakeCurrencyFormatter) { bind<CurrencyFormatter>() }
                singleOf(::OperationUiModelMapperImpl) { bind<OperationUiModelMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun toUIModel_maps_operation_correctly() {
        // Arrange
        val operation = Operation(
            id = "1",
            title = "Op 1",
            amount = 10.0,
            category = "Cat",
            date = LocalDate(2023, 10, 5)
        )

        // Act
        val result = mapper.toUIModel(operation)

        // Assert
        assertEquals("Op 1", result.title)
        assertEquals("05/10/2023", result.date)
        assertEquals("10.0 Formatted", result.balance)
    }

    @Test
    fun toUIModelList_maps_list_correctly() {
        // Arrange
        val operation = Operation(title = "Op 1", amount = 0.0)
        val list = listOf(operation)

        // Act
        val result = mapper.toUIModelList(list)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Op 1", result.first().title)
    }
}
