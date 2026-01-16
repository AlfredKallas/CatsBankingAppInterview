package com.example.catsbankingapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import kotlinx.serialization.json.Json

actual class HttpClientFactory {
    actual fun create(config: AppConfig, json: Json): HttpClient {
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