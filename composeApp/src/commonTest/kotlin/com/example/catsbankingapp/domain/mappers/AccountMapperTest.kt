package com.example.catsbankingapp.domain.mappers

import com.example.catsbankingapp.data.models.AccountModel
import com.example.catsbankingapp.data.models.OperationModel
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

class AccountMapperTest : KoinTest {

    private val accountMapper: AccountMapper by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single<TimeZone> { TimeZone.UTC }
                singleOf(::DateTimeParserImpl) { bind<DateTimeParser>() }
                singleOf(::OperationMapperImpl) { bind<OperationMapper>() }
                singleOf(::AccountMapperImpl) { bind<AccountMapper>() }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun toAccount_maps_model_correctly() {
        // Arrange
        val opModel = OperationModel("1", "Title", "10", "Cat", "1696464000000")
        val accountModel = AccountModel(
            id = "123",
            label = "My Account",
            balance = 100.0,
            operations = arrayListOf(opModel)
        )

        // Act
        val result = accountMapper.toAccount(accountModel)

        // Assert
        assertEquals("123", result.id)
        assertEquals("My Account", result.label)
        assertEquals(100.0, result.balance)
        assertEquals(1, result.operations!!.size)
        assertEquals("Title", result.operations.first().title)
    }

    @Test
    fun toAccountList_maps_list_correctly() {
        // Arrange
        val accountModel = AccountModel(id = "1")
        val list = listOf(accountModel)

        // Act
        val result = accountMapper.toAccountList(list)

        // Assert
        assertEquals(1, result.size)
        assertEquals("1", result.first().id)
    }
}
