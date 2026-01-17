package com.example.catsbankingapp.di

import com.example.catsbankingapp.utils.DateTimeFormatter
import com.example.catsbankingapp.utils.DateTimeFormatterImpl
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
import kotlinx.datetime.TimeZone
import org.koin.dsl.module

val dateTimeModule = module {
    single {
        TimeZone.currentSystemDefault()
    }

    factory <DateTimeFormatter> {
        DateTimeFormatterImpl()
    }

    factory<DateTimeParser> {
        DateTimeParserImpl(get())
    }
}