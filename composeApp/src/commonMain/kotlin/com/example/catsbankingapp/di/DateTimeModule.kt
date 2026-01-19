package com.example.catsbankingapp.di

import com.example.catsbankingapp.utils.DateFormatter
import com.example.catsbankingapp.utils.DateFormatterImpl
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
import kotlinx.datetime.TimeZone
import org.koin.dsl.module

val dateTimeModule = module {
    single {
        TimeZone.currentSystemDefault()
    }

    factory <DateFormatter> {
        DateFormatterImpl()
    }

    factory<DateTimeParser> {
        DateTimeParserImpl(get())
    }
}