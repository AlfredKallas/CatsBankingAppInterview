
package com.example.catsbankingapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

actual class HttpClientFactory {
    actual fun create(config: AppConfig, json: Json): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    retryOnConnectionFailure(true)
                    connectTimeout(30, TimeUnit.SECONDS)
                }
            }
            configureCommon(config, json)
        }
    }
}