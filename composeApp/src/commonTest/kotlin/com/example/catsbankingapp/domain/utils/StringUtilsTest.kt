package com.example.catsbankingapp.domain.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilsTest {

    @Test
    fun formatAndConvertToDouble_converts_comma_to_dot() {
        val input = "12,34"
        val result = input.formatAndConvertToDouble()
        assertEquals(12.34, result)
    }

    @Test
    fun formatAndConvertToDouble_handles_dot_correctly() {
        val input = "12.34"
        val result = input.formatAndConvertToDouble()
        assertEquals(12.34, result)
    }

    @Test
    fun formatAndConvertToDouble_returns_zero_for_null() {
        val input: String? = null
        val result = input.formatAndConvertToDouble()
        assertEquals(0.0, result)
    }

    @Test
    fun formatAndConvertToDouble_returns_zero_for_invalid_string() {
        val input = "abc"
        val result = input.formatAndConvertToDouble()
        assertEquals(0.0, result)
    }

    @Test
    fun formatAndConvertToDouble_handles_empty_string() {
        val input = ""
        val result = input.formatAndConvertToDouble()
        assertEquals(0.0, result)
    }
}
