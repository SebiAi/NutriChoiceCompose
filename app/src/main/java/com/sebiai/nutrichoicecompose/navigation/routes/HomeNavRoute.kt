package com.sebiai.nutrichoicecompose.navigation.routes

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.HomeScreen
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
internal object HomeNavRoute

fun NavController.navigateToHomeScreen() {
    navigate(HomeNavRoute)
}
fun NavController.navigateToHomeScreenWithPopUp(
    fromRoute: Any
) {
    navigate(HomeNavRoute) {
        popUpTo(fromRoute) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.homeScreenDestination(
    sharedHomeAndSearchResultsScreenViewModel: HomeAndSearchResultsScreenViewModel,

    onFoodCardClicked: (AFood) -> Unit,
    onNavSearchResults: () -> Unit,
    nutritionPreferences: NutritionPreferences,
    filterPreferences: FilterPreferences,

    onShowSnackBar: (String) -> Unit
) {
    composable<HomeNavRoute> {
        HomeScreen(
            modifier = Modifier.padding(12.dp),
            viewModel = sharedHomeAndSearchResultsScreenViewModel,

            onFoodCardClicked = onFoodCardClicked,
            afterSearchPerformed = onNavSearchResults,
            nutritionPreferences = nutritionPreferences,
            filterPreferences = filterPreferences,

            onShowSnackBar = onShowSnackBar
        )
    }
}