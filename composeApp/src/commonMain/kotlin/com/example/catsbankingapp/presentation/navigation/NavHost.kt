package com.example.catsbankingapp.presentation.navigation

import androidx.annotation.MainThread
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catsbankingapp.presentation.accounts.AccountsScreen
import com.example.catsbankingapp.presentation.mainscreen.MainScreen
import com.example.catsbankingapp.presentation.operations.OperationsListScreen
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        NavHost(
            navController = navController,
            startDestination = MainScreen,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                )
            },
            popEnterTransition = {
                fadeIn(initialAlpha = 0.4f)
            },
            popExitTransition = {
                scaleOut(
                    targetScale = 0.9f,
                    transformOrigin = TransformOrigin(pivotFractionX = 0.9f, pivotFractionY = 0.5f)
                )
            }
        ) {
            composable<MainScreen> {
                ScreenContent {
                    MainScreen(onButtonClicked = {
                        navController.navigateSafely(AccountsList)
                    })
                }
            }

            composable<AccountsList> {
                ScreenContent {
                    AccountsScreen(
                        navigateToAccountScreen = {
                            navController.navigateSafely(OperationsForAccount(it))
                        },
                        onBackNavigation = {
                            navController.popBackStack()
                        })
                }
            }
            composable<OperationsForAccount>(
                typeMap = mapOf(
                    typeOf<String>() to NavType.StringType
                )
            ) {
                ScreenContent {
                    OperationsListScreen(
                        onBackNavigation = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenContent(content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(24.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        content()
    }
}

@MainThread
@JvmOverloads
fun <T : Any>NavController.navigateSafely(
    route: T,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    if (this.currentBackStackEntry.lifecycleIsResumed()) {
        navigate(route, navOptions, navigatorExtras)
    }
}

private fun NavBackStackEntry?.lifecycleIsResumed(): Boolean =
    this?.lifecycle?.currentState == Lifecycle.State.RESUMED