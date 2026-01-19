package com.example.catsbankingapp.utils

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

interface StringProvider {
    suspend fun getString(resource: StringResource): String
}

class StringProviderImpl : StringProvider {
    override suspend fun getString(resource: StringResource): String {
        return org.jetbrains.compose.resources.getString(resource)
    }
}
