package com.example.catsbankingapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.catsbankingapp.core.CatsBankingException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<Result<T>>.mapOnSuccess(block: (T) -> R): Flow<Result<R>> =
    this.flatMapConcat {
        if (it.isSuccess){
            val value = it.getOrNull() as T
            flowOf(Result.success(block(value)))
        } else {
            flowOf(Result.failure(it.exceptionOrNull() as CatsBankingException))
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<Result<T>>.onSuccess(block: (T) -> Unit): Flow<Result<T>> =
    this.flatMapConcat {
        if (it.isSuccess){
            val value = it.getOrNull()
            value?.let {
                block(value)
            }
        }
        this@onSuccess
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<Result<T>>.mapResultOnSuccess(block: (T) -> Result<R>): Flow<Result<R>> =
    this.flatMapConcat {
        if (it.isSuccess){
            val value = it.getOrNull() as T
            flowOf(block(value))
        } else {
            flowOf(Result.failure(it.exceptionOrNull() as CatsBankingException))
        }
    }

@Composable
fun  <T> Flow<T>.ObserveLifecycleAwareEvents(block: (T) -> Unit){
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@ObserveLifecycleAwareEvents.collect {
                block(it)
            }
        }
    }
}