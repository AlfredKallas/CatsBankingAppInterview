package com.example.catsbankingapp.domain.mappers

import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.data.models.BankModel
import com.example.catsbankingapp.utils.DateTimeParser
import com.example.catsbankingapp.utils.DateTimeParserImpl
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

class BankMapperTest : KoinTest {

    private val bankMapper: BankMapper by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
                singleOf(::AccountMapperImpl) { bind<AccountMapper>() }
                singleOf(::BankMapperImpl) { bind<BankMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun toBank_maps_model_correctly() {
        // Arrange
        val accountModel = AccountModel(id = "1")
        val bankModel = BankModel(
            name = "My Bank",
            isCA = true,
            accounts = arrayListOf(accountModel)
        )

        // Act
        val result = bankMapper.toBank(bankModel)

        // Assert
        assertEquals("My Bank", result.name)
        assertTrue(result.isCA)
        assertEquals(1, result.accounts.size)
        assertEquals("1", result.accounts.first().id)
    }

    @Test
    fun toBankList_maps_list_correctly() {
        // Arrange
        val bankModel = BankModel(name = "Bank 1")
        val list = listOf(bankModel)

        // Act
        val result = bankMapper.toBankList(list)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Bank 1", result.first().name)
    }
}
