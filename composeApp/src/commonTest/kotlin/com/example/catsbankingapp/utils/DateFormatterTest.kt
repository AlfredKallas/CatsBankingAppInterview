package com.example.catsbankingapp.utils

import kotlinx.datetime.LocalDate
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFormatterTest : KoinTest {

    private lateinit var dateFormatter: DateFormatter

    @BeforeTest
    fun setup() {
        dateFormatter = DateFormatterImpl()
    }

    @Test
    fun `format returns formatted date string`() {
        // Arrange
        val date = LocalDate(2023, 10, 5)

        // Act
        val result = dateFormatter.format(date)

        // Assert
        assertEquals("05/10/2023", result)
    }
}
