package com.example.catsbankingapp.utils

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf
 val navigationJson = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    explicitNulls = false
}


inline fun <reified T> createNavTypePair(type: NavType<T> = createAppNavType<T>()) =
    typeOf<T>() to type

inline fun <reified T> createAppNavType(allowNulls: Boolean = false) =
    object : NavType<T>(isNullableAllowed = allowNulls) {
        override fun put(bundle: SavedState, key: String, value: T) {
            bundle.write { putString(key, navigationJson.encodeToString(value)) }
        }
        override fun get(bundle: SavedState, key: String): T? =
            bundle.read { navigationJson.decodeFromString(getString(key)) }
        override fun parseValue(value: String): T =
            navigationJson.decodeFromString(value)
        override fun serializeAsValue(value: T): String =
            navigationJson.encodeToString(value)
    }