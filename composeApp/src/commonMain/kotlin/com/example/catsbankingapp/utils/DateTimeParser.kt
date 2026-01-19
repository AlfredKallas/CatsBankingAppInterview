package com.example.catsbankingapp.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

interface DateTimeParser {
    fun parse(epochMilliseconds: Long): LocalDate
}

class DateTimeParserImpl(val timeZone: TimeZone) : DateTimeParser {
    override fun parse(epochMilliseconds: Long): LocalDate {
        val instant = Instant.fromEpochMilliseconds(epochMilliseconds)
        return instant.toLocalDateTime(timeZone).date
    }
}