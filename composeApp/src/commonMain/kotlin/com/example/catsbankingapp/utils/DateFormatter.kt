package com.example.catsbankingapp.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

interface DateFormatter {
    fun format(localDate: LocalDate): String
}

class DateFormatterImpl : DateFormatter {

    val DEFAULT_DATE_FORMAT: DateTimeFormat<LocalDate> = LocalDate.Format {
        day()
        char('/')
        monthNumber()
        char('/')
        year()
    }

    var dateTimeFormatter: DateTimeFormat<LocalDate> = DEFAULT_DATE_FORMAT

    override fun format(localDate: LocalDate): String {
        return localDate.format(dateTimeFormatter)
    }
}