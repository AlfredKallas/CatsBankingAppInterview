package com.example.catsbankingapp.domain

import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.data.FakeBanksListRepository
import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.domain.mappers.AccountMapper
import com.example.catsbankingapp.domain.mappers.AccountMapperImpl
import com.example.catsbankingapp.domain.mappers.BankMapper
import com.example.catsbankingapp.domain.mappers.BankMapperImpl
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

class GetBanksListUseCaseTest : KoinTest {

    private val useCase: GetBanksListUseCase by inject()
    private val fakeRepository: FakeBanksListRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
                singleOf(::AccountMapperImpl) { bind<AccountMapper>() }
                singleOf(::BankMapperImpl) { bind<BankMapper>() }
                
                // Fake Repo
                // Fake Repo
                single { FakeBanksListRepository() }
                single<BanksListRepository> { get<FakeBanksListRepository>() }
                
                // UseCase
                singleOf(::GetBanksListUseCase)
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getBanksList_partitions_banks_correctly() = runTest {
        // Arrange
        val caBank = BankModel(name = "CA Bank", isCA = true)
        val otherBank = BankModel(name = "Other Bank", isCA = false)
        fakeRepository.banksListResult = Result.success(listOf(caBank, otherBank))

        // Act
        val result = useCase.getBanksList().first()

        // Assert
        assertTrue(result.isSuccess)
        val banksList = result.getOrNull()
        assertTrue(banksList != null)
        assertEquals(1, banksList.CABanks.size)
        assertEquals("CA Bank", banksList.CABanks.first().name)
        assertEquals(1, banksList.otherBanks.size)
        assertEquals("Other Bank", banksList.otherBanks.first().name)
    }
}
