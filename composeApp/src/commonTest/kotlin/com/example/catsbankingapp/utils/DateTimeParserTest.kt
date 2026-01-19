package com.example.catsbankingapp.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeParserTest : KoinTest {

    private lateinit var dateTimeParser: DateTimeParser

    @BeforeTest
    fun setup() {
        dateTimeParser = DateTimeParserImpl(TimeZone.UTC)
    }

    @Test
    fun `parse returns correct LocalDate for epoch`() {
        // Arrange
        val epochMillis = 1696464000000L // 2023-10-05T00:00:00Z

        // Act
        val result = dateTimeParser.parse(epochMillis)

        // Assert
        assertEquals(LocalDate(2023, 10, 5), result)
    }
}
