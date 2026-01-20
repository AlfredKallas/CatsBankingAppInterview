package com.example.catsbankingapp.presentation.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.catsbankingapp.presentation.tests.tags.loadingscreen.LoadingScreenSelectors

@Composable
fun LoadingScreen(){
    Box(
        modifier = Modifier
            .testTag(LoadingScreenSelectors.LoadingScreenTag)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}