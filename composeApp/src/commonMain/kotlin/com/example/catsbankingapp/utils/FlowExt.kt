package com.example.catsbankingapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.example.catsbankingapp.core.CatsBankingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("UNCHECKED_CAST")
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
@Suppress("UNCHECKED_CAST")
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
fun  <T> Flow<T>.ObserveLifecycleAwareEvents(onEvent: (T) -> Unit){
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate){
                this@ObserveLifecycleAwareEvents.collect(onEvent)
            }
        }
    }
}

suspend fun <T> safeRunSuspend(
    block: suspend () -> T,
    onNoneCancellationException: suspend (Throwable) -> Unit = {}
): Result<T> = runCatching { block() }.onFailure {
        if (it is CancellationException) throw it
        onNoneCancellationException(it)
}

context(viewModel: ViewModel)
fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
    return stateIn(
        scope = viewModel.viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initialValue,
    )
}