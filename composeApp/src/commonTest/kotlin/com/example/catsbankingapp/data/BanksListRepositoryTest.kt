package com.example.catsbankingapp.data

import com.example.catsbankingapp.di.testAppModule
import com.example.catsbankingapp.data.models.BankModel
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BanksListRepositoryTest : KoinTest {

    private val repository: BanksListRepository by inject()
    private val fakeLocalDataSource: FakeLocalBanksListDataSource by inject()
    
    // We handle the mock engine content via a mutable property since injection happens once
    private var mockResponseContent: String = "[]"
    private var mockResponseStatus: HttpStatusCode = HttpStatusCode.OK

    private val testDispatcher = StandardTestDispatcher()

    private val mockEngine = MockEngine { _ ->
        respond(
            content = ByteReadChannel(mockResponseContent),
            status = mockResponseStatus,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    @BeforeTest
    fun setup() {
        startKoin {
            modules(testAppModule(testDispatcher, mockEngine))
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getBanksList_returns_local_data_when_available() = runTest(testDispatcher) {
        // Arrange
        val localData = listOf(BankModel(name = "Local Bank"))
        fakeLocalDataSource.localBanks = localData
        
        // Mock Response shouldn't be hit, but safe default
        mockResponseContent = "[]" 

        // Act
        val result = repository.getBanksList().first()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(localData, result.getOrNull())
    }

    @Test
    fun getBanksList_fetches_from_network_and_saves_to_local_when_local_is_empty() = runTest(testDispatcher) {
        // Arrange
        fakeLocalDataSource.localBanks = null // Local empty

        val networkDataJson = """
            [
                {"name": "Network Bank", "isCA": 1, "accounts": []}
            ]
        """.trimIndent()
        
        // Set mock response
        mockResponseContent = networkDataJson

        // Act
        val result = repository.getBanksList().first()

        // Assert
        assertTrue(result.isSuccess)
        val banks = result.getOrNull()
        assertTrue(banks != null && banks.isNotEmpty())
        assertEquals("Network Bank", banks.first().name)
        
        // Verify saved to local
        assertEquals("Network Bank", fakeLocalDataSource.localBanks?.first()?.name)
    }
}
