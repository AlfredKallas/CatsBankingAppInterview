package com.example.catsbankingapp.domain.mappers

import com.example.catsbankingapp.data.models.OperationModel
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
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

class OperationMapperTest : KoinTest {

    private val operationMapper: OperationMapper by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `toOperation maps model correctly`() {
        // Arrange
        val model = OperationModel(
            id = "1",
            title = "Test Op",
            amount = "10.50",
            category = "Food",
            date = "1696464000000" // 2023-10-05
        )

        // Act
        val result = operationMapper.toOperation(model)

        // Assert
        assertEquals("1", result.id)
        assertEquals("Test Op", result.title)
        assertEquals(10.50, result.amount)
        assertEquals("Food", result.category)
        assertEquals(LocalDate(2023, 10, 5), result.date)
    }

    @Test
    fun `toOperationList maps list and sorts if comparator provided`() {
        // Arrange
        val model1 = OperationModel("1", "A", "10", "Cat", "1696464000000")
        val model2 = OperationModel("2", "B", "20", "Cat", "1696550400000")
        val list = listOf(model2, model1)
        
        // Act
        val result = operationMapper.toOperationList(list, compareBy { it.title }) // Sort by title

        // Assert
        assertEquals(2, result.size)
        assertEquals("A", result[0].title)
        assertEquals("B", result[1].title)
    }
}
