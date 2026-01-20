package com.example.catsbankingapp.presentation.accounts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AccountsViewModelTest : KoinTest {

    private val viewModel: AccountsViewModel by inject()
    private val fakeFactory = FakeAccountsPresenterFactory()


    val fakePresenter by lazy {
        fakeFactory.fakePresenter
    }


    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin {
            modules(module {
                single<AccountsPresenterFactory> { fakeFactory }
                singleOf(::AccountsViewModel)
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun init_calls_getBanksList_on_presenter() = runTest {
        // Accessing viewModel triggers init
        val vm = viewModel

        // Assert
        advanceUntilIdle()
        assertTrue(fakePresenter.getBanksUIListCalled)
    }
}
