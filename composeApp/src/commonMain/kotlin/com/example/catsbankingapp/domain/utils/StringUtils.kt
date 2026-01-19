package com.example.catsbankingapp.domain.utils

fun String?.formatAndConvertToDouble() =
    this?.
    replace(",", ".")?.
    toDoubleOrNull() ?: 0.0