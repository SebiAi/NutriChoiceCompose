package com.sebiai.nutrichoicecompose.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.OnboardingScreen
import kotlinx.serialization.Serializable

@Serializable
internal object OnboardingNavRoute

fun NavController.navigateToOnboardingScreen() {
    navigate(OnboardingNavRoute)
}

fun NavGraphBuilder.onboardingScreenDestination(
    onOnboardingComplete: (NutritionPreferences, FilterPreferences) -> Unit
) {
    composable<OnboardingNavRoute> {
        OnboardingScreen(
            onOnboardingComplete = onOnboardingComplete
        )
    }
}