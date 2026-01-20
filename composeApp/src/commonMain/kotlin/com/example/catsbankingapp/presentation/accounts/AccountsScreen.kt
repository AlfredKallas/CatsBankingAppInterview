package com.example.catsbankingapp.presentation.accounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.catsbankingapp.presentation.accounts.models.AccountUIModel
import com.example.catsbankingapp.presentation.accounts.models.BankSectionUIModel
import com.example.catsbankingapp.presentation.accounts.models.BankUIModel
import com.example.catsbankingapp.presentation.accounts.models.BanksListScreenUIModel
import com.example.catsbankingapp.presentation.error.ErrorScreen
import com.example.catsbankingapp.presentation.loading.LoadingScreen
import com.example.catsbankingapp.presentation.tests.tags.accountscreen.AccountScreenSelectors
import com.example.catsbankingapp.utils.ObserveLifecycleAwareEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    modifier: Modifier = Modifier,
    navigateToAccountScreen: (String) -> Unit = {}
) {
    val viewModel = koinViewModel<AccountsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.events.ObserveLifecycleAwareEvents {
        when (it) {
            is AccountsEvents.OnAccountClicked -> navigateToAccountScreen.invoke(it.accountId)
            is AccountsEvents.OnRetryClicked -> viewModel.getBanksList()
        }
    }
    AccountsScreen(modifier, uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    modifier: Modifier = Modifier,
    uiState: BanksListScreenUIState
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.screenTitle,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        )

                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (uiState) {
                is BanksListScreenUIState.Loading -> LoadingScreen()
                is BanksListScreenUIState.Success -> AccountsScreenContent(
                    modifier = Modifier,
                    banksList = uiState.banksList
                )
                is BanksListScreenUIState.Error -> ErrorScreen(
                    message = uiState.message,
                    onRetry = uiState.onRetry
                )
            }
        }
    }
}

@Composable
fun AccountsScreenContent(
    modifier: Modifier = Modifier,
    banksList: BanksListScreenUIModel
) {
    LazyColumn(
        modifier = modifier
            .testTag(AccountScreenSelectors.AccountScreenTag)
            .fillMaxSize()
    ) {
        if (banksList.CABankSection.banks.isNotEmpty()) {
            // CA Bank Section
            item {
                Text(
                    modifier = Modifier
                        .testTag(AccountScreenSelectors.CABanksSectionHeaderTag)
                        .padding(top = 4.dp, bottom = 4.dp, start = 8.dp),
                    text = banksList.CABankSection.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            itemsIndexed(
                items = banksList.CABankSection.banks,
                key = { _, bank -> bank.title }
            ) { index, bank ->
                BankAccountCard(
                    modifier = Modifier.testTag(AccountScreenSelectors.CABankTag(index)),
                    bank = bank,
                    isCABank = true,
                    isNotLastItem = index < banksList.CABankSection.banks.lastIndex
                )
            }
        }
        if (banksList.otherBanksSection.banks.isNotEmpty()) {
            // Other Banks Section
            item {
                Text(
                    modifier = Modifier
                        .testTag(AccountScreenSelectors.OtherBanksSectionHeaderTag)
                        .padding(top = 24.dp, bottom = 4.dp, start = 8.dp),
                    text = banksList.otherBanksSection.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            itemsIndexed(banksList.otherBanksSection.banks) { index, bank ->
                BankAccountCard(
                    modifier = Modifier.testTag(AccountScreenSelectors.OtherBankTag(index)),
                    bank = bank,
                    isCABank = false,
                    isNotLastItem = index < banksList.otherBanksSection.banks.lastIndex
                )
            }
        }
    }
}

@Composable
private fun BankAccountCard(
    modifier: Modifier = Modifier,
    expandedByDefault: Boolean = false,
    bank: BankUIModel,
    isCABank: Boolean,
    isNotLastItem: Boolean
) {
    // make sure the expanded value survive the configuration changes
    var expanded by rememberSaveable { mutableStateOf(expandedByDefault) }
    // expandable header
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = bank.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = bank.totalAccountsBalances,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Crossfade(
                    targetState = expanded,
                    modifier = Modifier.padding(start = 20.dp),
                    content = { expanded ->
                        val arrow = if (expanded) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        }
                        Icon(
                            imageVector = arrow,
                            contentDescription = null
                        )
                    }
                )

            }

            // expandable contents
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween()
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween()
                )
            ) {
                HorizontalDivider(
                    Modifier.fillMaxWidth()
                        .padding(start = 8.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    bank.accounts.forEachIndexed { index, account ->
                        val bankAccountTag = if (isCABank) {
                            AccountScreenSelectors.CABanksAccountTag(index)
                        } else {
                            AccountScreenSelectors.OtherBanksAccountTag(index)
                        }
                        AccountCard(
                            modifier = Modifier.testTag(bankAccountTag),
                            account = account
                        )
                        if (index < bank.accounts.lastIndex) {
                            HorizontalDivider(
                                Modifier.fillMaxWidth()
                                    .padding(
                                        start = 32.dp
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
    if (isNotLastItem) {
        HorizontalDivider(
            Modifier.fillMaxWidth().padding(start = 8.dp)
        )
    }
}

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    account: AccountUIModel
) {
    Row(
        modifier.fillMaxWidth()
            .clickable {
                account.onClick(account.title)
            }
            .padding(
                start = 32.dp,
                top = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            ),
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = account.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = account.accountBalance,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }

    }
}

@Preview
@Composable
fun AccountsScreenContentPreview() {
    val banksList = BanksListScreenUIModel(
        CABankSection = BankSectionUIModel(
            title = "CA",
            banks = listOf(
                BankUIModel(
                    title = "CA America",
                    accounts = listOf(
                        AccountUIModel(
                            title = "Checking",
                            accountBalance = "$100.00",
                            onClick = { }
                        ),
                        AccountUIModel(
                            title = "Savings",
                            accountBalance = "$200.00",
                            onClick = { }
                        )
                    ),
                    totalAccountsBalances = "$300.00"
                ),
                BankUIModel(
                    title = "Wells Fargo",
                    accounts = listOf(
                        AccountUIModel(
                            title = "Checking",
                            accountBalance = "$10",
                            onClick = {}
                        ),
                        AccountUIModel(
                            title = "Savings",
                            accountBalance = "$20",
                            onClick = {}
                        )
                    ),
                    totalAccountsBalances = "$30"
                )
            ),
        ),
        otherBanksSection = BankSectionUIModel(
            title = "Other Banks",
            banks = listOf(
                BankUIModel(
                    title = "Bank of America",
                    accounts = listOf(
                        AccountUIModel(
                            title = "Checking",
                            accountBalance = "$100.00",
                            onClick = { }
                        ),
                        AccountUIModel(
                            title = "Savings",
                            accountBalance = "$200.00",
                            onClick = { }
                        )
                    ),
                    totalAccountsBalances = "$300.00"
                ),
            )
        )
    )
    val uiState = BanksListScreenUIState.Success("mes Comptes", banksList)
    MaterialTheme {
        AccountsScreen(
            uiState = uiState
        )
    }
}

@Preview
@Composable
fun AccountsScreenLoadingPreview() {
    val uiState = BanksListScreenUIState.Loading("mes Comptes")
    MaterialTheme {
        AccountsScreen(
            uiState = uiState
        )
    }
}

@Preview
@Composable
fun AccountsScreenErrorPreview() {
    val uiState = BanksListScreenUIState.Error("mes Comptes", "An error occurred")
    MaterialTheme {
        AccountsScreen(
            uiState = uiState
        )
    }
}



