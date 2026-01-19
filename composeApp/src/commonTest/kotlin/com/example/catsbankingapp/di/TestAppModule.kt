package com.example.catsbankingapp.di

import com.example.catsbankingapp.core.local.LocalBanksListDataSource
import com.example.catsbankingapp.core.network.AppConfig
import com.example.catsbankingapp.core.network.NetworkClient
import com.example.catsbankingapp.data.BanksListRepository
import com.example.catsbankingapp.data.BanksListRepositoryImpl
import com.example.catsbankingapp.data.FakeLocalBanksListDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun testAppModule(
    testDispatcher: TestDispatcher,
    mockEngine: MockEngine
): Module = module {
    // Test Dispatcher
    single<CoroutineDispatcher> { testDispatcher }

    // App Config
    single { AppConfig(true, "http://localhost") }

    // Fake Data Source
    singleOf(::FakeLocalBanksListDataSource) { bind<LocalBanksListDataSource>() }

    // HttpClient with MockEngine
    single {
        HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    // Network Client
    single { NetworkClient(get(), get(), get()) }

    // Repository
    single<BanksListRepository> { BanksListRepositoryImpl(get(), get()) }
}
