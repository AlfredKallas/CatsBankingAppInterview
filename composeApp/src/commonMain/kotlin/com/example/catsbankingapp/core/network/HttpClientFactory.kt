package com.example.catsbankingapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// 1. The Expect declaration
interface HttpClientFactory {
    fun create(config: AppConfig, json: Json): HttpClient
}

expect fun buildHttpClientFactory(): HttpClientFactory

private const val CALL_REQUEST_TIMOUT: Long = 20_000
private const val CALL_CONNECTION_TIMOUT: Long = 20_000

// 2. Shared configuration logic (Extension function to avoid duplication)
fun <T : HttpClientEngineConfig>HttpClientConfig<T>.configureCommon(config: AppConfig, json: Json) {
    install(ContentNegotiation) {
        json(json)
    }

    install(Logging) {
        println(config.isDebug)
        level = if (config.isDebug) LogLevel.ALL else LogLevel.NONE
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = CALL_REQUEST_TIMOUT
        connectTimeoutMillis = CALL_CONNECTION_TIMOUT
    }

    defaultRequest {
        url(config.baseUrl)
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}