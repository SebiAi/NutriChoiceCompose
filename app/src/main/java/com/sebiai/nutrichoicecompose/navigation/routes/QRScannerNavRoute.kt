package com.sebiai.nutrichoicecompose.navigation.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sebiai.nutrichoicecompose.screens.QRScannerScreen
import kotlinx.serialization.Serializable

@Serializable
internal object QRScannerNavRoute

fun NavController.navigateToQRScannerScreen() {
    navigate(QRScannerNavRoute)
}

fun NavGraphBuilder.qrScannerScreenDestination() {
    composable<QRScannerNavRoute> {
        QRScannerScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}