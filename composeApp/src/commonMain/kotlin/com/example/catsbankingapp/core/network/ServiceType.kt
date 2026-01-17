package com.example.catsbankingapp.core.network

import io.ktor.http.HttpMethod

interface ServiceType<T> {
    val path: String
        get() = ""
    val headers: Map<String, String>
        get() = emptyMap()
    val queryParameters: Map<String, String>
        get() = emptyMap()
    val method: HttpMethod
}