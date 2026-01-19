package com.example.catsbankingapp.data.serializer

import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Serializable
data class BooleanAsIntMock(
    @Serializable(with = BooleanAsIntSerializer::class)
    val value: Boolean
)

class BooleanAsIntSerializerTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun deserialize_converts_1_to_true() {
        val jsonString = """{"value": 1}"""
        val result = json.decodeFromString<BooleanAsIntMock>(jsonString)
        assertTrue(result.value)
    }

    @Test
    fun deserialize_converts_0_to_false() {
        val jsonString = """{"value": 0}"""
        val result = json.decodeFromString<BooleanAsIntMock>(jsonString)
        assertFalse(result.value)
    }

    @Test
    fun deserialize_converts_other_int_to_false() {
        val jsonString = """{"value": 5}"""
        val result = json.decodeFromString<BooleanAsIntMock>(jsonString)
        assertFalse(result.value)
    }

    @Test
    fun serialize_converts_true_to_1() {
        val mock = BooleanAsIntMock(true)
        val result = json.encodeToString(BooleanAsIntMock.serializer(), mock)
        assertEquals("""{"value":1}""", result)
    }

    @Test
    fun serialize_converts_false_to_0() {
        val mock = BooleanAsIntMock(false)
        val result = json.encodeToString(BooleanAsIntMock.serializer(), mock)
        assertEquals("""{"value":0}""", result)
    }
}
