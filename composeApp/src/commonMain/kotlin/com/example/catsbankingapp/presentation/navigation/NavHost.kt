package com.example.catsbankingapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.TransformOrigin
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
    NavHost(
        navController = navController,
        startDestination = MainScreen,
        enterTransition = { slideInHorizontally(initialOffsetX = { it/2 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it/2 }) + fadeOut() },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = {  scaleOut(
            targetScale = 0.7F,
            transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f))
        }
    ) {
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