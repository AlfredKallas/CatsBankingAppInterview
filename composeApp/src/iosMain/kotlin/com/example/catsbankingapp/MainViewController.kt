package com.example.catsbankingapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.uikit.EndEdgePanGestureBehavior
import androidx.compose.ui.window.ComposeUIViewController

@OptIn(ExperimentalComposeUiApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {  endEdgePanGestureBehavior = EndEdgePanGestureBehavior.Back }
) { App() }