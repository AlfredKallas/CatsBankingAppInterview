package com.example.catsbankingapp.presentation.operations

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.example.catsbankingapp.presentation.navigation.OperationsForAccount
import com.example.catsbankingapp.utils.NavArgsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.jvm.JvmSuppressWildcards
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OperationsListViewModelTest : KoinTest {

    private val fakeNavArgsProvider = object : NavArgsProvider {
        override fun <T : Any> provideArg(
            route: KClass<T>,
            typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>
        ): T {
            return OperationsForAccount(
                accountId = "123"
            ) as T
        }

        override fun provideOriginalHandle(): SavedStateHandle {
            TODO("No Implementation for tests")
        }

    }

    private val fakePresenter = FakeOperationsListPresenter()
    
    // Manual setup for SavedStateHandle is easier than Koin injection for this specific parameter
    private lateinit var viewModel: OperationsListViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = OperationsListViewModel(fakePresenter, fakeNavArgsProvider)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_calls_getAccountOperationsList_on_presenter_with_correct_accountId() = runTest {
        // Init happens in BeforeTest
        
        // Assert
        assertEquals("123", fakePresenter.lastAccountIdRequested)
    }
}
