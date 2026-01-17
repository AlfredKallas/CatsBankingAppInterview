package com.example.catsbankingapp.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

interface DateTimeFormatter {
    fun format(localDateTime: LocalDateTime): String
}

class DateTimeFormatterImpl : DateTimeFormatter {

    val DEFAULT_DATE_FORMAT: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        day()
        char('/')
        monthNumber()
        char('/')
        year()
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    var dateTimeFormatter: DateTimeFormat<LocalDateTime> = DEFAULT_DATE_FORMAT

    override fun format(localDateTime: LocalDateTime): String {
        return localDateTime.format(dateTimeFormatter)
    }

    //In case the caller wants to use another Format
    fun setDateFormat(dateFormat: DateTimeFormat<LocalDateTime>) {
        dateTimeFormatter = dateFormat
    }
}