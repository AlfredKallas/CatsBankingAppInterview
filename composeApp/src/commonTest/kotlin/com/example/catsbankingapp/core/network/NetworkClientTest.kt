package com.example.catsbankingapp.core.network

import com.example.catsbankingapp.core.CatsBankingException
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Serializable
data class MockResponse(val id: Int, val name: String)

class NetworkClientTest {

    private val appConfig = AppConfig(isDebug = true, baseUrl = "https://api.example.com")
    private val json = Json { ignoreUnknownKeys = true }

    private fun createClient(engine: MockEngine): NetworkClient {
        val httpClient = HttpClient(engine) {
            install(ContentNegotiation) {
                json(json)
            }
        }
        return NetworkClient(Dispatchers.Unconfined, appConfig, httpClient)
    }

    @Test
    fun performNetworkCall_returns_success_when_response_is_200() = runTest {
        val engine = MockEngine { _ ->
            respond(
                content = """{"id": 1, "name": "Test"}""",
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type", ContentType.Application.Json.toString())
            )
        }
        val client = createClient(engine)
        val serviceType = object : ServiceType<MockResponse> {
            override val path = "test"
            override val method = HttpMethod.Get
        }

        val result = client.performNetworkCall(serviceType).first()

        assertTrue(result.isSuccess)
        assertEquals(MockResponse(1, "Test"), result.getOrNull())
    }

    @Test
    fun performNetworkCall_returns_failure_when_response_is_404() = runTest {
        val engine = MockEngine { _ ->
            respond(
                content = "Not Found",
                status = HttpStatusCode.NotFound
            )
        }
        val client = createClient(engine)
        val serviceType = object : ServiceType<MockResponse> {
            override val path = "test"
            override val method = HttpMethod.Get
        }

        val result = client.performNetworkCall(serviceType).first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CatsBankingException.NotFoundException)
    }

    @Test
    fun performNetworkCall_returns_failure_when_JSON_is_malformed() = runTest {
        val engine = MockEngine { _ ->
            respond(
                content = """{"id": "not-an-int"}""",
                status = HttpStatusCode.OK,
                headers = headersOf("Content-Type", ContentType.Application.Json.toString())
            )
        }
        val client = createClient(engine)
        val serviceType = object : ServiceType<MockResponse> {
            override val path = "test"
            override val method = HttpMethod.Get
        }

        val result = client.performNetworkCall(serviceType).first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CatsBankingException.JsonParsingErrorException)
    }

    @Test
    fun performNetworkCall_returns_failure_when_response_is_401() = runTest {
        val engine = MockEngine { _ ->
            respond(
                content = "Unauthorized",
                status = HttpStatusCode.Unauthorized
            )
        }
        val client = createClient(engine)
        val serviceType = object : ServiceType<MockResponse> {
            override val path = "test"
            override val method = HttpMethod.Get
        }

        val result = client.performNetworkCall(serviceType).first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CatsBankingException.UnauthorizedException)
    }

    @Test
    fun performNetworkCall_returns_failure_when_response_is_500() = runTest {
        val engine = MockEngine { _ ->
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError
            )
        }
        val client = createClient(engine)
        val serviceType = object : ServiceType<MockResponse> {
            override val path = "test"
            override val method = HttpMethod.Get
        }

        val result = client.performNetworkCall(serviceType).first()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is CatsBankingException.InternalServerErrorException)
    }
}
