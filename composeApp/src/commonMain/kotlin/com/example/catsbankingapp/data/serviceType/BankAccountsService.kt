package com.example.catsbankingapp.data.serviceType

import com.example.catsbankingapp.core.network.ServiceType
import com.example.catsbankingapp.data.models.BankModel
import io.ktor.http.HttpMethod

class BankAccountsService : ServiceType<List<BankModel>> {
    override val path: String
        get() = "/banks.json"
    override val method: HttpMethod
        get() = HttpMethod.Get
}