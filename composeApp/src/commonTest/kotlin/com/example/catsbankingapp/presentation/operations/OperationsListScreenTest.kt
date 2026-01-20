package com.example.catsbankingapp.presentation.operations

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.example.catsbankingapp.RobolectricTest
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import com.example.catsbankingapp.presentation.tests.tags.errorscreen.ErrorScreenSelectors
import com.example.catsbankingapp.presentation.tests.tags.loadingscreen.LoadingScreenSelectors
import com.example.catsbankingapp.presentation.tests.tags.operationslistscreen.AccountOperationsListScreenSelectors
import com.example.catsbankingapp.utils.FakeNavArgsProvider
import com.example.catsbankingapp.utils.NavArgsProvider
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class OperationsListScreenTest: RobolectricTest() {

    private val fakePresenterFactory = FakeOperationsListPresenterFactory()
    private val fakePresenter by lazy {
        fakePresenterFactory.fakePresenter
    }


    @BeforeTest
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single<OperationsListPresenterFactory> { fakePresenterFactory }
                single<NavArgsProvider> { FakeNavArgsProvider(accountId = "123") }

                single { OperationsListViewModel(get(), get()) }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun loading_state_shows_loading_screen() = runComposeUiTest {
        // Arrange
        fakePresenter._uiState.value = OperationsListUIState.Loading

        // Act
        setContent {
            OperationsListScreen()
        }

        onNodeWithTag(LoadingScreenSelectors.LoadingScreenTag).assertIsDisplayed()
    }

    @Test
    fun success_state_shows_account_details() = runComposeUiTest {
        // Arrange
        val model = AccountOperationsScreenModel(
            accountTitle = "My Checking Account",
            totalBalance = "1234.56 €",
            operations = emptyList()
        )
        fakePresenter._uiState.value = OperationsListUIState.Success(model)

        // Act
        setContent {
            OperationsListScreen()
        }

        // Assert
        onNodeWithTag(AccountOperationsListScreenSelectors.TotalBalanceTag).assertIsDisplayed()
        onNodeWithTag(AccountOperationsListScreenSelectors.TotalBalanceTag).assertTextEquals("1234.56 €")
        onNodeWithTag(AccountOperationsListScreenSelectors.AccountTitleTag).assertIsDisplayed()
        onNodeWithTag(AccountOperationsListScreenSelectors.AccountTitleTag).assertTextEquals("My Checking Account")
    }

    @Test
    fun error_state_shows_error_message() = runComposeUiTest {
        // Arrange

        // Act
        setContent {
            OperationsListScreen()
        }
        waitForIdle()

        fakePresenter._uiState.value = OperationsListUIState.Error("Failed to load operations")

        waitForIdle()


        // Assert
        onNodeWithTag(ErrorScreenSelectors.ErrorScreenTag).assertIsDisplayed()
        onNodeWithTag(ErrorScreenSelectors.ErrorScreenTextTag).assertIsDisplayed()
        onNodeWithTag(ErrorScreenSelectors.ErrorScreenTextTag).assertTextEquals("Error: Failed to load operations")
        onNodeWithTag(ErrorScreenSelectors.ErrorScreenRetryBtn).assertIsDisplayed()
    }
}
