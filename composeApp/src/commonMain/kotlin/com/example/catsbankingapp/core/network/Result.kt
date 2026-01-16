package com.example.catsbankingapp.core.network

sealed class Result<out T> {
    data class success<T>(val data: T) : Result<T>()
    data class failure(val exception: CatsBankingException) : Result<Nothing>()
}
