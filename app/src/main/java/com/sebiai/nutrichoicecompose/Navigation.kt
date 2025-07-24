package com.sebiai.nutrichoicecompose

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sebiai.nutrichoicecompose.screens.GeneralSearchScreen
import com.sebiai.nutrichoicecompose.screens.SettingsScreen

enum class Destination(
    val route: String,
    @field:StringRes
    val titleStringRes: Int
) {
    GENERAL_SEARCH("GENERAL_SEARCH", R.string.app_name),
    SETTINGS("SETTINGS", R.string.settings_screen_title)
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.GENERAL_SEARCH -> GeneralSearchScreen()
                    Destination.SETTINGS -> SettingsScreen()
                }
            }
        }
    }
}