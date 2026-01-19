package com.example.catsbankingapp.presentation.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import catsbankingapp.composeapp.generated.resources.Error_Screen_Retry_Btn_Title
import catsbankingapp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ErrorScreen(title: String = stringResource(Res.string.Error_Screen_Retry_Btn_Title), message: String, onRetry: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Error: $message")
        Button(onClick = onRetry){
            Text(title)
        }
    }
}