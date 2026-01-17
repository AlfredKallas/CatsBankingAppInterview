package com.example.catsbankingapp.presentation.operations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardBackspace
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.catsbankingapp.presentation.error.ErrorScreen
import com.example.catsbankingapp.presentation.loading.LoadingScreen
import com.example.catsbankingapp.presentation.operations.models.AccountOperationsScreenModel
import com.example.catsbankingapp.presentation.operations.models.OperationUIModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperationsListScreen(modifier: Modifier = Modifier, onBackNavigation: () -> Unit = {}) {
    val viewModel = koinViewModel<OperationsListViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val navigationEvenState = rememberNavigationEventState<NavigationEventInfo>(
//        currentInfo = NavigationEventInfo.None,
//    )
//    NavigationEventInfo
//    NavigationBackHandler(
//        state = navState,
//        isBackEnabled = true,
//        onBackCancelled = {
//            // Process the canceled back gesture
//        },
//        onBackCompleted = {
//            // Process the completed back gesture
//        }
//    )
//    LaunchedEffect(navState.transitionState) {
//        val transitionState = navState.transitionState
//        if (transitionState is NavigationEventTransitionState.InProgress) {
//            val progress = transitionState.latestEvent.progress
//            // Animate the back gesture progress
//        }
//    }
    LaunchedEffect(Unit){
        viewModel.events.collect {
            when(it) {
                is OperationsListEvents.OnRetryClicked -> viewModel.getAccountOperationsList()
            }
        }
    }
    OperationsListScreen(modifier, uiState)
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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
                .weight(0.2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = accountOperationsScreenModel.accountTitle,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = accountOperationsScreenModel.totalBalance,
                style = MaterialTheme.typography.titleMedium
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()
            .weight(0.8f)) {
            items(accountOperationsScreenModel.operations) { operation ->
                AccountOperationCard(operation = operation)
            }
        }
    }
}

@Composable
fun AccountOperationCard(
    modifier: Modifier = Modifier,
    operation: OperationUIModel
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = contentColorFor(MaterialTheme.colorScheme.primary),
        )
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = operation.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
                Text(
                    text = operation.date.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondary,
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
            totalBalance = "1000.00",
            operations = listOf(
                OperationUIModel(
                    title = "Opération 1",
                    date = "12/01/2023",
                    balance = "100.00"
                ),
                OperationUIModel(
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