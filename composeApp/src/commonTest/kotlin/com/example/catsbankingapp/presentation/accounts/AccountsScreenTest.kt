package com.example.catsbankingapp.presentation.accounts

import androidx.compose.ui.test.*
import com.example.catsbankingapp.RobolectricTest
import com.example.catsbankingapp.presentation.accounts.models.*
import com.example.catsbankingapp.presentation.tests.tags.accountscreen.AccountScreenSelectors
import com.example.catsbankingapp.presentation.tests.tags.errorscreen.ErrorScreenSelectors
import com.example.catsbankingapp.presentation.tests.tags.loadingscreen.LoadingScreenSelectors
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.*
import kotlinx.coroutines.test.runTest
import app.cash.turbine.test

@OptIn(ExperimentalTestApi::class)
class AccountsScreenTest : RobolectricTest(), KoinTest {

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
    fun loading_state_shows_loading_screen() = runComposeUiTest {
        // Arrange
        fakePresenter._uiState.value = BanksListScreenUIState.Loading("Loading Title")

        // Act
        setContent {
            AccountsScreen()
        }

        // Assert
        onNodeWithTag(LoadingScreenSelectors.LoadingScreenTag).assertIsDisplayed()
    }

    @Test
    fun success_state_shows_bank_sections_and_items() = runComposeUiTest {
        // Arrange
        val banksList = BanksListScreenUIModel(
            CABankSection = BankSectionUIModel(
                title = "CA Section",
                banks = listOf(
                    BankUIModel(
                        title = "CA Bank 1",
                        accounts = listOf(
                            AccountUIModel(title = "CA Acc 1", accountBalance = "10€", onClick = {})
                        ),
                        totalAccountsBalances = "10€"
                    )
                )
            ),
            otherBanksSection = BankSectionUIModel(
                title = "Other Section",
                banks = listOf(
                    BankUIModel(
                        title = "Other Bank 1",
                        accounts = listOf(
                            AccountUIModel(title = "Other Acc 1", accountBalance = "20€", onClick = {})
                        ),
                        totalAccountsBalances = "20€"
                    )
                )
            )
        )
        fakePresenter._uiState.value = BanksListScreenUIState.Success("Success Title", banksList)

        // Act
        setContent {
            AccountsScreen()
        }
        waitForIdle()

        // Assert Headers
        onNodeWithTag(AccountScreenSelectors.CABanksSectionHeaderTag).assertIsDisplayed()
        onNodeWithTag(AccountScreenSelectors.OtherBanksSectionHeaderTag).assertIsDisplayed()

        // Assert Banks (collapsed)
        onNodeWithTag(AccountScreenSelectors.CABankTag(0)).assertIsDisplayed()
        onNodeWithTag(AccountScreenSelectors.OtherBankTag(0)).assertIsDisplayed()

        // Expand CA Bank
        onNodeWithTag(AccountScreenSelectors.CABankTag(0)).performClick()
        waitForIdle()
        onNodeWithTag(AccountScreenSelectors.CABanksAccountTag(0)).assertIsDisplayed()

        // Expand Other Bank
        onNodeWithTag(AccountScreenSelectors.OtherBankTag(0)).performClick()
        waitForIdle()
        onNodeWithTag(AccountScreenSelectors.OtherBanksAccountTag(0)).assertIsDisplayed()
    }

    @Test
    fun success_state_hides_empty_sections() = runComposeUiTest {
        // Arrange
        val banksList = BanksListScreenUIModel(
            CABankSection = BankSectionUIModel(title = "CA", banks = emptyList()),
            otherBanksSection = BankSectionUIModel(title = "Other", banks = emptyList())
        )
        fakePresenter._uiState.value = BanksListScreenUIState.Success("Success Title", banksList)

        // Act
        setContent {
            AccountsScreen()
        }
        waitForIdle()

        // Assert
        onNodeWithTag(AccountScreenSelectors.CABanksSectionHeaderTag).assertDoesNotExist()
        onNodeWithTag(AccountScreenSelectors.OtherBanksSectionHeaderTag).assertDoesNotExist()
    }

    @Test
    fun success_state_shows_only_ca_banks() = runComposeUiTest {
        // Arrange
        val banksList = BanksListScreenUIModel(
            CABankSection = BankSectionUIModel(
                title = "CA Section",
                banks = listOf(
                    BankUIModel(title = "CA 1", accounts = emptyList(), totalAccountsBalances = "1€")
                )
            ),
            otherBanksSection = BankSectionUIModel(title = "Other", banks = emptyList())
        )
        fakePresenter._uiState.value = BanksListScreenUIState.Success("Success", banksList)

        // Act
        setContent {
            AccountsScreen()
        }
        waitForIdle()

        // Assert
        onNodeWithTag(AccountScreenSelectors.CABanksSectionHeaderTag).assertIsDisplayed()
        onNodeWithTag(AccountScreenSelectors.OtherBanksSectionHeaderTag).assertDoesNotExist()
    }

    @Test
    fun success_state_shows_only_other_banks() = runComposeUiTest {
        // Arrange
        val banksList = BanksListScreenUIModel(
            CABankSection = BankSectionUIModel(title = "CA", banks = emptyList()),
            otherBanksSection = BankSectionUIModel(
                title = "Other Section",
                banks = listOf(
                    BankUIModel(title = "Other 1", accounts = emptyList(), totalAccountsBalances = "2€")
                )
            )
        )
        fakePresenter._uiState.value = BanksListScreenUIState.Success("Success", banksList)

        // Act
        setContent {
            AccountsScreen()
        }
        waitForIdle()

        // Assert
        onNodeWithTag(AccountScreenSelectors.CABanksSectionHeaderTag).assertDoesNotExist()
        onNodeWithTag(AccountScreenSelectors.OtherBanksSectionHeaderTag).assertIsDisplayed()
    }

    @Test
    fun error_state_shows_error_screen_and_retry_triggers_event() = runComposeUiTest {
        // Arrange
        fakePresenter._uiState.value = BanksListScreenUIState.Error("Error Title", "Network Error")

        // Act
        setContent {
            AccountsScreen()
        }
        waitForIdle()

        // Assert
        onNodeWithTag(ErrorScreenSelectors.ErrorScreenTag).assertIsDisplayed()
        onNodeWithText("Error: Network Error").assertIsDisplayed()

        // Act: Click Retry
        onNodeWithTag(ErrorScreenSelectors.ErrorScreenRetryBtn).performClick()

        // Assert: Verify presenter call
        assertTrue(fakePresenter.getBanksUIListCalled)
    }

    @Test
    fun account_click_triggers_navigation_event() = runComposeUiTest {
        // Arrange
        val banksList = BanksListScreenUIModel(
            CABankSection = BankSectionUIModel(
                title = "CA Section",
                banks = listOf(
                    BankUIModel(
                        title = "CA Bank 1",
                        accounts = listOf(
                            AccountUIModel(
                                title = "Account 123",
                                accountBalance = "50€",
                                onClick = { fakePresenter.onAccountClicked(it) }
                            )
                        ),
                        totalAccountsBalances = "50€"
                    )
                )
            ),
            otherBanksSection = BankSectionUIModel(title = "Other", banks = emptyList())
        )
        fakePresenter._uiState.value = BanksListScreenUIState.Success("Success Title", banksList)

        // Act
        setContent {
            AccountsScreen()
        }
        waitForIdle()

        // Expand Bank Card
        onNodeWithTag(AccountScreenSelectors.CABankTag(0)).assertIsDisplayed()
        onNodeWithTag(AccountScreenSelectors.CABankTag(0)).performClick()
        waitForIdle()

        // Click Account
        onNodeWithTag(AccountScreenSelectors.CABanksAccountTag(0)).performClick()
        waitForIdle()

        // Assert
        runTest {
            fakePresenter.events.test {
                val event = awaitItem()
                assertTrue(event is AccountsEvents.OnAccountClicked)
                // Note: The onClick in AccountsScreen passes account.title as accountId currently
                // onClick = { account.onClick(account.title) }
                assertEquals("Account 123", event.accountId)
            }
        }
    }
}
