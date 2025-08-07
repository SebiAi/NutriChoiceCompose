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
import com.sebiai.nutrichoicecompose.screens.SearchResultsScreen
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
internal object SearchResultsNavRoute

fun NavController.navigateToSearchResultsScreen() {
    navigate(SearchResultsNavRoute)
}

fun NavGraphBuilder.searchResultsScreenDestination(
    sharedHomeAndSearchResultsScreenViewModel: HomeAndSearchResultsScreenViewModel,

    onFoodCardClicked: (AFood) -> Unit,
    nutritionPreferences: NutritionPreferences,
    filterPreferences: FilterPreferences,

    onShowSnackBar: (String) -> Unit
) {
    composable<SearchResultsNavRoute> {
        SearchResultsScreen(
            modifier = Modifier.padding(12.dp),
            viewModel = sharedHomeAndSearchResultsScreenViewModel,

            onFoodCardClicked = onFoodCardClicked,
            nutritionPreferences = nutritionPreferences,
            filterPreferences = filterPreferences,

            onShowSnackBar = onShowSnackBar
        )
    }
}