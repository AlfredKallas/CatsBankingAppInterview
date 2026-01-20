package com.example.catsbankingapp.presentation.operations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.example.catsbankingapp.presentation.error.ErrorScreen
import com.example.catsbankingapp.presentation.loading.LoadingScreen
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import com.example.catsbankingapp.presentation.operations.models.OperationUIModel
import com.example.catsbankingapp.utils.ObserveLifecycleAwareEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperationsListScreen(
    modifier: Modifier = Modifier,
    onBackNavigation: () -> Unit = {}
) {
    val viewModel = koinViewModel<OperationsListViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvenState = rememberNavigationEventState<NavigationEventInfo>(
        currentInfo = NavigationEventInfo.None,
    )
    val onComposedBackNavigation: () -> Unit = {
        onBackNavigation.invoke()
    }

    NavigationBackHandler(
        state = navigationEvenState,
        isBackEnabled = true,
        onBackCompleted = {
            onComposedBackNavigation.invoke()
        }
    )

    viewModel.events.ObserveLifecycleAwareEvents {
        when(it) {
            is OperationsListEvents.OnRetryClicked -> viewModel.getAccountOperationsList()
        }
    }

    OperationsListScreen(modifier, uiState,
        onBackNavigation = onComposedBackNavigation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperationsListScreen(
    modifier: Modifier = Modifier,
    uiState: OperationsListUIState,
    onBackNavigation: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackNavigation
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardBackspace,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (uiState) {
                is OperationsListUIState.Loading -> LoadingScreen()
                is OperationsListUIState.Success ->
                    AccountOperationsScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        accountOperationsScreenModel = uiState.accountOperationsScreenModel
                    )
                is OperationsListUIState.Error ->
                    ErrorScreen(
                        message = uiState.message,
                        onRetry = uiState.onRetry
                    )
            }
        }
    }
}

@Composable
fun AccountOperationsScreenContent(
    modifier: Modifier = Modifier,
    accountOperationsScreenModel: AccountOperationsScreenModel
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = accountOperationsScreenModel.totalBalance,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = accountOperationsScreenModel.accountTitle,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        itemsIndexed(
            items = accountOperationsScreenModel.operations,
            key = { _, operation -> operation.id }
        ) { index, operation ->
            AccountOperationCard(operation = operation)
            if (index < accountOperationsScreenModel.operations.lastIndex) {
                HorizontalDivider(
                    modifier.fillMaxWidth()
                        .padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun AccountOperationCard(
    modifier: Modifier = Modifier,
    operation: OperationUIModel
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = operation.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
                Text(
                    text = operation.date.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
            }
            Text(
                text = operation.balance,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }
    }
}

@Preview
@Composable
fun AccountOperationsScreenContentPreview() {
        val accountOperationScreenModel = AccountOperationsScreenModel(
            accountTitle = "Compte 1",
            totalBalance = "1000.00 €",
            operations = listOf(
                OperationUIModel(
                    id = "ID1",
                    title = "Opération 1",
                    date = "12/01/2023",
                    balance = "100.00"
                ),
                OperationUIModel(
                    id = "ID2",
                    title = "Opération 2",
                    date = "13/01/2023",
                    balance = "200.00"
                )
            )
        )

        val  uiState = OperationsListUIState.Success(accountOperationScreenModel)
        MaterialTheme {
            OperationsListScreen(
                uiState = uiState
            )
        }
}

@Preview
@Composable
fun AccountsScreenLoadingPreview() {
    val  uiState = OperationsListUIState.Loading
    MaterialTheme {
        OperationsListScreen(
            uiState = uiState
        )
    }
}

@Preview
@Composable
fun AccountsScreenErrorPreview() {
    val  uiState = OperationsListUIState.Error("An error occurred")
    MaterialTheme {
        OperationsListScreen(
            uiState = uiState
        )
    }
}