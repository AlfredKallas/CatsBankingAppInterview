package com.example.catsbankingapp.utils

import com.example.catsbankingapp.core.CatsBankingException
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FlowExtTest : KoinTest {

    @Test
    fun `mapOnSuccess transforms value on success`() = runTest {
        // Arrange
        val flow = flowOf(Result.success(5))

        // Act
        val result = flow.mapOnSuccess { it * 2 }.toList()

        // Assert
        assertEquals(10, result.first().getOrNull())
    }

    @Test
    fun `mapOnSuccess passes failure through`() = runTest {
        // Arrange
        val exception = CatsBankingException.UnknownErrorException("test")
        val flow = flowOf(Result.failure<Int>(exception))

        // Act
        val result = flow.mapOnSuccess { it * 2 }.toList()

        // Assert
        assertTrue(result.first().isFailure)
        assertEquals(exception, result.first().exceptionOrNull())
    }

    @Test
    fun `mapResultOnSuccess transforms value to result on success`() = runTest {
        // Arrange
        val flow = flowOf(Result.success(5))

        // Act
        val result = flow.mapResultOnSuccess { Result.success(it * 2) }.toList()

        // Assert
        assertEquals(10, result.first().getOrNull())
    }
}
