package com.example.catsbankingapp.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

interface DateTimeParser {
    fun parse(epochMilliseconds: Long): LocalDateTime
}

class DateTimeParserImpl(val timeZone: TimeZone) : DateTimeParser {
    override fun parse(epochMilliseconds: Long): LocalDateTime {
        val instant = Instant.fromEpochMilliseconds(epochMilliseconds)
        return instant.toLocalDateTime(timeZone)
    }
}