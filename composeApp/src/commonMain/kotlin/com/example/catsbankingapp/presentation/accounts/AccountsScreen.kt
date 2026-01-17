package com.example.catsbankingapp.presentation.accounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(modifier: Modifier = Modifier, navigateToAccountScreen: (String) -> Unit = {}) {
    val viewModel = koinViewModel<AccountsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel){
        viewModel.events.collect {
            when(val event = it) {
                is AccountsEvents.OnAccountClicked -> navigateToAccountScreen.invoke(event.accountId)
            }
        }
    }

    AccountsScreen(modifier, uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(modifier: Modifier = Modifier, uiState: BanksListScreenUIState) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = { TopAppBar(title = { Text(uiState.screenTitle) }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (val state = uiState) {
                is BanksListScreenUIState.Loading -> LoadingScreen()
                is BanksListScreenUIState.Success -> AccountsScreenContent(banksList = state.banksList)
                is BanksListScreenUIState.Error -> ErrorScreen(state.message)
            }
        }
    }
}

@Composable
fun AccountsScreenContent(
    modifier: Modifier = Modifier,
    banksList: BanksListScreenUIModel
) {
    Column(modifier = modifier) {
        BankAccountsSection(
            account = banksList.CABankSection
        )
        BankAccountsSection(
            account = banksList.otherBanksSection
        )
    }
}

@Composable
fun BankAccountsSection(modifier: Modifier = Modifier, account: BankSectionUIModel) {
    Column(modifier = modifier) {
        Text( modifier = Modifier.padding( horizontal = 8.dp), text = account.title)
        LazyColumn {
            items(account.banks) { bank ->
                BankAccountCard(
                    bank = bank
                )
            }
        }
    }
}

@Composable
private fun BankAccountCard(
    modifier: Modifier = Modifier,
    expandedByDefault: Boolean = false,
    bank: BankUIModel
) {
    // make sure the expanded value survive the configuration changes
    var expanded by rememberSaveable { mutableStateOf(expandedByDefault) }
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "expandable-arrow"
    )

    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors()
    ) {
        Column {
            // expandable header
            Surface(
                modifier = Modifier
                    .clickable { expanded = !expanded },
                color = MaterialTheme.colorScheme.primary
            ) {
                Column {
                    Row(
                        Modifier.fillMaxWidth()
                            .padding(8.dp),
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
                        Icon(
                            modifier = Modifier
                                .rotate(arrowRotation)
                                .padding(horizontal = 20.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
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
                        AccountsList(
                            accountsList = bank.accounts
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccountsList(
    accountsList: List<AccountUIModel>
) {
    Column {
        accountsList.forEach { account ->
            AccountCard(
                account = account
            )
        }
    }
}

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    account: AccountUIModel
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                account.onClick(account.title)
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = account.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
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
        val  uiState = BanksListScreenUIState.Success("mes Comptes", banksList)
        MaterialTheme {
            AccountsScreen(
                uiState = uiState
            )
        }
}

@Preview
@Composable
fun AccountsScreenLoadingPreview() {
    val  uiState = BanksListScreenUIState.Loading("mes Comptes")
    MaterialTheme {
        AccountsScreen(
            uiState = uiState
        )
    }
}

@Preview
@Composable
fun AccountsScreenErrorPreview() {
    val  uiState = BanksListScreenUIState.Error("mes Comptes", "An error occurred")
    MaterialTheme {
        AccountsScreen(
            uiState = uiState
        )
    }
}




