package com.example.catsbankingapp.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import com.example.catsbankingapp.presentation.navigation.OperationsForAccount
import kotlin.reflect.KClass
import kotlin.reflect.KType

class FakeNavArgsProvider(private val accountId: String = "123") : NavArgsProvider {
    override fun <T : Any> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @kotlin.jvm.JvmSuppressWildcards NavType<*>>
    ): T {
        return OperationsForAccount(accountId) as T
    }

    override fun provideOriginalHandle(): SavedStateHandle {
        return SavedStateHandle()
    }
}
