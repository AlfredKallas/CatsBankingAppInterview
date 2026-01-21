package com.example.catsbankingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catsbankingapp.presentation.accounts.AccountsScreen
import com.example.catsbankingapp.presentation.mainscreen.MainScreen
import com.example.catsbankingapp.presentation.operations.OperationsListScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

// Creates routes
@Serializable
object MainScreen

@Serializable
object AccountsList

@Serializable
data class OperationsForAccount(val accountId: String)

@Composable
fun MainAppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreen) {
        composable<MainScreen> {
            MainScreen(onButtonClicked = {
                navController.navigate(AccountsList)
            })
        }

        composable<AccountsList> {
            AccountsScreen(
                navigateToAccountScreen = {
                    navController.navigate(OperationsForAccount(it))
            },
            onBackNavigation = {
                navController.popBackStack()
            })
        }
        composable<OperationsForAccount>(
            typeMap = mapOf(
                typeOf<String>() to NavType.StringType
            )
        ) {
            OperationsListScreen(
                onBackNavigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}