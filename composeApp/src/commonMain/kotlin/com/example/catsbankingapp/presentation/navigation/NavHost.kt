package com.example.catsbankingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catsbankingapp.presentation.accounts.AccountsScreen
import kotlinx.serialization.Serializable

// Creates routes
@Serializable
object AccountsList

@Serializable
data class OperationsForAccount(val accountId: String)

@Composable
fun mainAppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AccountsList) {
        composable<AccountsList> { AccountsScreen()}
        composable<OperationsForAccount> { }
    }
}