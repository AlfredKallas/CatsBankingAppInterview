package com.example.catsbankingapp.utils

import kotlinx.coroutines.CoroutineDispatcher

class FakeTestDispatcherProvider(val testDispatcher: CoroutineDispatcher): DispatchersProvider {
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val mainImmediate: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}