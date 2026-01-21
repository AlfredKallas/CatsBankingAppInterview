package com.example.catsbankingapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

import com.example.catsbankingapp.presentation.navigation.MainAppNavHost

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainAppNavHost()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}