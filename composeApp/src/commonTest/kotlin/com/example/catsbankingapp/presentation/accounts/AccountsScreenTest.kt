package com.example.catsbankingapp.presentation.accounts

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.example.catsbankingapp.RobolectricTest
import com.example.catsbankingapp.presentation.accounts.models.BankSectionUIModel
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class AccountsScreenTest: RobolectricTest(), KoinTest {

    private val fakeAccountsPresenterFactory = FakeAccountsPresenterFactory()
    private val fakePresenter = fakeAccountsPresenterFactory.fakePresenter


    @BeforeTest
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single<AccountsPresenterFactory> { fakeAccountsPresenterFactory }
                single { AccountsViewModel(get()) }
            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun loading_state_shows_loading_title() = runComposeUiTest {
        // Arrange
        fakePresenter._uiState.value = BanksListScreenUIState.Loading("Loading Title")

        // Act
        setContent {
            AccountsScreen()
        }

        // Assert
        onNodeWithText("Loading Title").assertExists()
    }

    @Test
    fun success_state_shows_bank_sections() = runComposeUiTest {
        // Arrange
        val banksList = BanksListScreenUIModel(
            CABankSection = BankSectionUIModel(
                title = "CA Section",
                banks = emptyList()
            ),
            otherBanksSection = BankSectionUIModel(
                title = "Other Section",
                banks = emptyList()
            )
        )
        fakePresenter._uiState.value = BanksListScreenUIState.Success("Success Title", banksList)

        // Act
        setContent {
            AccountsScreen()
        }

        // Assert
        onNodeWithText("Success Title").assertExists()
        onNodeWithText("CA Section").assertExists()
        onNodeWithText("Other Section").assertExists()
    }

    @Test
    fun error_state_shows_error_message() = runComposeUiTest {
        // Arrange
        fakePresenter._uiState.value = BanksListScreenUIState.Error("Error Title", "Something went wrong")

        // Act
        setContent {
            AccountsScreen()
        }

        // Assert
        onNodeWithText("Error: Something went wrong").assertExists()
    }
}
