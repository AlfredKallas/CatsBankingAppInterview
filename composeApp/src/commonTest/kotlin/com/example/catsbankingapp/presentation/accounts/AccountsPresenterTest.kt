package com.example.catsbankingapp.presentation.accounts

import app.cash.turbine.test
import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.data.FakeBanksListRepository
import com.example.catsbankingapp.domain.GetBanksListUseCase
import com.example.catsbankingapp.domain.mappers.AccountMapper
import com.example.catsbankingapp.domain.mappers.AccountMapperImpl
import com.example.catsbankingapp.domain.mappers.BankMapper
import com.example.catsbankingapp.domain.mappers.BankMapperImpl
import com.example.catsbankingapp.domain.mappers.OperationMapper
import com.example.catsbankingapp.domain.mappers.OperationMapperImpl
import com.example.catsbankingapp.presentation.accounts.mappers.BanksListScreenMapper
import com.example.catsbankingapp.presentation.accounts.mappers.FakeBanksListScreenMapper
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
import com.example.catsbankingapp.utils.FakeStringProvider
import com.example.catsbankingapp.utils.StringProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccountsPresenterTest : KoinTest {

    private val presenterFactory: AccountsPresenterFactory by inject()
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
                singleOf(::AccountMapperImpl) { bind<AccountMapper>() }
                singleOf(::BankMapperImpl) { bind<BankMapper>() }
                single { FakeBanksListRepository() }
                single<BanksListRepository> { get<FakeBanksListRepository>() }
                singleOf(::GetBanksListUseCase)
                single { FakeStringProvider() }
                single<StringProvider> { get<FakeStringProvider>() }
                singleOf(::FakeBanksListScreenMapper) { bind<BanksListScreenMapper>() }
                singleOf(::AccountsPresenterFactoryImpl) { bind<AccountsPresenterFactory>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getBanksUIList_updates_state_to_Success() = runTest(testDispatcher) {
        // Arrange
        val presenter: AccountsPresenter = presenterFactory.create(this)
        // Act & Assert
        presenter.uiState.test {
            // Initial state (Loading)
            assertEquals("", (awaitItem() as BanksListScreenUIState.Loading).title)

            presenter.getBanksUIList()

            // State after getBanksUIList starts (Loading with title)
            assertEquals("Fake String", (awaitItem() as BanksListScreenUIState.Loading).title)

            // Final state (Success)
            assertTrue(awaitItem() is BanksListScreenUIState.Success)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}
