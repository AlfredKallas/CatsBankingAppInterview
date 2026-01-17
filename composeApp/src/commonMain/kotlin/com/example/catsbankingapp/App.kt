package com.example.catsbankingapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

import com.example.catsbankingapp.presentation.navigation.mainAppNavHost

@Composable
@Preview
fun App() {
    MaterialTheme {
        mainAppNavHost()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}