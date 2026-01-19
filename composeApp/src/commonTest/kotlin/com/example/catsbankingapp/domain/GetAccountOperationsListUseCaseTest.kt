package com.example.catsbankingapp.domain

import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.data.FakeBanksListRepository
import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.domain.mappers.AccountMapper
import com.example.catsbankingapp.domain.mappers.AccountMapperImpl
import com.example.catsbankingapp.domain.mappers.OperationMapper
import com.example.catsbankingapp.domain.mappers.OperationMapperImpl
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
import kotlinx.coroutines.flow.first
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

class GetAccountOperationsListUseCaseTest : KoinTest {

    private val useCase: GetAccountOperationsListUseCase by inject()
    private val fakeRepository: FakeBanksListRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
                singleOf(::AccountMapperImpl) { bind<AccountMapper>() }
                
                // Fake Repo
                single { FakeBanksListRepository() }
                single<BanksListRepository> { get<FakeBanksListRepository>() }
                
                // UseCase
                singleOf(::GetAccountOperationsListUseCase)
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAccountOperationsList_returns_account_when_found() = runTest {
        // Arrange
        val accountId = "123"
        val accountModel = AccountModel(id = accountId, label = "Test Account")
        val bankModel = BankModel(accounts = arrayListOf(accountModel))
        fakeRepository.banksListResult = Result.success(listOf(bankModel))

        // Act
        val result = useCase.getAccountOperationsList(accountId).first()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals("Test Account", result.getOrNull()?.label)
    }

    @Test
    fun getAccountOperationsList_returns_error_when_account_not_found() = runTest {
        // Arrange
        fakeRepository.banksListResult = Result.success(emptyList())

        // Act
        val result = useCase.getAccountOperationsList("999").first()

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Account not found", result.exceptionOrNull()?.message)
    }
}
