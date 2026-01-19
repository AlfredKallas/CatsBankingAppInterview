package com.example.catsbankingapp.utils

import org.jetbrains.compose.resources.StringResource

class FakeStringProvider : StringProvider {
    override suspend fun getString(resource: StringResource): String {
        return "Fake String"
    }
}
