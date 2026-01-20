package com.example.catsbankingapp.presentation.operations

import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.data.FakeBanksListRepository
import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.domain.GetAccountOperationsListUseCase
import com.example.catsbankingapp.domain.mappers.AccountMapper
import com.example.catsbankingapp.domain.mappers.AccountMapperImpl
import com.example.catsbankingapp.domain.mappers.OperationMapper
import com.example.catsbankingapp.domain.mappers.OperationMapperImpl
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapper
import com.example.catsbankingapp.presentation.operations.mappers.AccountOperationsScreenModelMapperImpl
import com.example.catsbankingapp.presentation.operations.mappers.OperationUiModelMapper
import com.example.catsbankingapp.presentation.operations.mappers.OperationUiModelMapperImpl
import com.example.catsbankingapp.utils.DateFormatter
import com.example.catsbankingapp.utils.DateFormatterImpl
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
import com.example.catsbankingapp.utils.currencyformatter.CurrencyFormatter
import com.example.catsbankingapp.utils.currencyformatter.FakeCurrencyFormatter
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
import kotlin.test.assertTrue

import app.cash.turbine.test
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope

class OperationsListPresenterTest : KoinTest {

    private val fakeRepo: FakeBanksListRepository by inject()

    private val presenterFactory: OperationsListPresenterFactory by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::DateFormatterImpl) { bind<DateFormatter>() }
                singleOf(::FakeCurrencyFormatter) { bind<CurrencyFormatter>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
                singleOf(::AccountMapperImpl) { bind<AccountMapper>() }
                singleOf(::OperationUiModelMapperImpl) { bind<OperationUiModelMapper>() }
                singleOf(::AccountOperationsScreenModelMapperImpl) { bind<AccountOperationsScreenModelMapper>() }
                
                single { FakeBanksListRepository() }
                single<BanksListRepository> { get<FakeBanksListRepository>() }
                singleOf(::GetAccountOperationsListUseCase)

                singleOf(::OperationsListPresenterFactoryImpl) { bind<OperationsListPresenterFactory>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAccountOperationsList_updates_state_to_Success() = runTest {
        // Arrange
        val accountId = "123"
        val bankModel = BankModel(accounts = arrayListOf(AccountModel(id = accountId)))
        fakeRepo.banksListResult = Result.success(listOf(bankModel))
        val presenter: OperationsListPresenter = presenterFactory.create(this)

        // Act & Assert
        presenter.uiState.test {
            // Initial state (Loading)
            assertTrue(awaitItem() is OperationsListUIState.Loading)

            presenter.getAccountOperationsList(accountId)

            // Final state (Success)
            assertTrue(awaitItem() is OperationsListUIState.Success)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}
