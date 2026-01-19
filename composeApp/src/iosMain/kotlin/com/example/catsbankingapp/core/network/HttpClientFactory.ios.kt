package com.example.catsbankingapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import kotlinx.serialization.json.Json

class iOSHttpClientFactory(): HttpClientFactory {

    override fun create(config: AppConfig, json: Json): HttpClient {
        return HttpClient(Darwin) {
            engine {
                configureRequest {
                    setAllowsCellularAccess(true)
                }
            }
            configureCommon(config, json)
        }
    }
}

actual fun buildHttpClientFactory(): HttpClientFactory = iOSHttpClientFactory()