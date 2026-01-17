package com.example.catsbankingapp.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<Result<T>>.mapOnSuccess(block: (T) -> R): Flow<Result<R>> =
    this.flatMapConcat {
        if (it.isSuccess){
            val value = it.getOrNull() as T
            flowOf(Result.success(block(value)))
        } else {
            emptyFlow()
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<Result<T>>.mapResultOnSuccess(block: (T) -> Result<R>): Flow<Result<R>> =
    this.flatMapConcat {
        if (it.isSuccess){
            val value = it.getOrNull() as T
            flowOf(block(value))
        } else {
            emptyFlow()
        }
    }