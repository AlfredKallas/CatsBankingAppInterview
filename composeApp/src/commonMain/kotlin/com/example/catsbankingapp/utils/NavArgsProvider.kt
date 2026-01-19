package com.example.catsbankingapp.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import kotlin.jvm.JvmSuppressWildcards
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * works only if used inside a viewmodel , do not exepct it to work outside a viewmodel or outside a place
 * that is not scoped to navGraph that uses [SavedStateHandle]
 * made to allow us to abstract the usage of navigation args provider
 * to make us not depend on Android OS Bundle which is hard to be mocked during tests
 * for the viewmodels that depends on [SavedStateHandle]
 */
interface NavArgsProvider {
    fun <T : Any> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    ): T

    /**
     * for debugging to make sure that [org.koin.core.Koin] provides exactly
     * same instance of [SavedStateHandle] scoped to the viewmodel that depends on [NavArgsProvider]
     *     private fun test() {
     *       val sameRef = ViewModel.savedStateHandle === navArgsProvider.provideOriginalHandle()
     *         println(sameStateRef) true
     *         val sameInstance = ViewModel.savedStateHandle == navArgsProvider.provideOriginalHandle()
     *         println(sameStateInst) true
     *     }
     */
    fun provideOriginalHandle(): SavedStateHandle
}

class DefaultNavArgsProvider(
    private val savedStateHandle: SavedStateHandle
) : NavArgsProvider {
    override fun <T : Any> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>
    ): T {
        return savedStateHandle.toRoute(route = route)
    }

    override fun provideOriginalHandle(): SavedStateHandle {
        return savedStateHandle
    }
}