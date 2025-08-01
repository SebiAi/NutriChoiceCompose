package com.sebiai.nutrichoicecompose.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.composables.FilterBottomSheet
import com.sebiai.nutrichoicecompose.composables.FoodCard
import com.sebiai.nutrichoicecompose.composables.FoodCardType
import com.sebiai.nutrichoicecompose.composables.SearchAndListComponent
import com.sebiai.nutrichoicecompose.composables.determineCustomizableChips
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.FilterState
import com.sebiai.nutrichoicecompose.dataclasses.Meal
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeAndSearchResultsScreenViewModel = viewModel(),

    onFoodCardClicked: (AFood) -> Unit,
    afterSearchPerformed: () -> Unit,
    nutritionPreferences: NutritionPreferences,
    filterPreferences: FilterPreferences,

    onShowSnackBar: (String) -> Unit
) {
    val uiState by viewModel.homeScreenUiState.collectAsStateWithLifecycle()
    val sharedUiState by viewModel.sharedSearchFunctionUiState.collectAsStateWithLifecycle()

    SearchAndListComponent(
        modifier = modifier,

        welcomeMessageHeading = stringResource(R.string.welcome_greeting),
        welcomeMessageSubtitle = stringResource(R.string.welcome_search_instructions),

        query = sharedUiState.searchQuery,
        onSearch = { query: String ->
            Log.d(null, "Searched with query \"$query\"")
            viewModel.performSearch(query, sharedUiState.filterState)
            afterSearchPerformed()
        },
        onQueryChanged = viewModel::updateSearchQuery,
        onClearQuery = { viewModel.updateSearchQuery("") },
        onFilterClicked = { viewModel.updateShowFilterBottomSheet(true) },
        showFilterBadge = sharedUiState.filterState != FilterState(filterPreferences),

        listHeading = @Composable {
            Text(
                text = stringResource(R.string.recently_viewed_heading),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        },
        isListEmpty = uiState.recentlyViewedFoods.isEmpty(),
        emptyListInfoTitle = stringResource(R.string.no_recently_viewed_heading),
        emptyListInfoSubtitle = stringResource(R.string.no_recently_viewed_subtitle),

        listScrollState = uiState.recentlyViewedScrollState
    ) {
        items(items = uiState.recentlyViewedFoods) {
            FoodCard(
                modifier = Modifier.clickable(
                    onClick = { onFoodCardClicked(it) }
                ),
                type = FoodCardType.BIG,
                image = it.getImage(LocalContext.current),
                title = it.title,
                priceString = it.getPriceString(LocalContext.current),
                isRestaurantFood = it is Meal,
                customizableChips = determineCustomizableChips(LocalContext.current, it, nutritionPreferences)
            )
        }
    }

    val filterAppliedMessage = stringResource(R.string.filter_applied)
    if (sharedUiState.showFilterBottomSheet) {
        FilterBottomSheet(
            initialFilterState = sharedUiState.filterState,
            filterPreferences = filterPreferences,
            onSaveFilter = {
                viewModel.updateFilterState(it)
                onShowSnackBar(filterAppliedMessage)
            },

            onDismissRequest = {
                viewModel.updateShowFilterBottomSheet(false)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    NutriChoiceComposeTheme {
        HomeScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            onFoodCardClicked = {},
            afterSearchPerformed = {},
            nutritionPreferences = NutritionPreferences(
                protein = true,
                carbs = true,
                fat = true,
                calories = true,
                ecoFriendly = true,
                healthy = true
            ),
            filterPreferences = FilterPreferences(),

            onShowSnackBar = {}
        )
    }
}