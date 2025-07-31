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
import androidx.compose.ui.text.font.FontStyle
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
import com.sebiai.nutrichoicecompose.dataclasses.Meal
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeAndSearchResultsScreenViewModel = viewModel(),

    onFoodCardClicked: (AFood) -> Unit,
    nutritionPreferences: NutritionPreferences,
    filterPreferences: FilterPreferences,

    onShowSnackBar: (String) -> Unit
) {
    val uiState by viewModel.searchAndResultsScreenUiState.collectAsStateWithLifecycle()
    val sharedUiState by viewModel.sharedSearchFunctionUiState.collectAsStateWithLifecycle()

    SearchAndListComponent(
        modifier = modifier,

        query = sharedUiState.searchQuery,
        onSearch = { query: String ->
                Log.d(null, "Searched with query \"$query\"")
                viewModel.performSearch(query, sharedUiState.filterState)
        },
        onQueryChanged = viewModel::updateSearchQuery,
        onClearQuery = { viewModel.updateSearchQuery("") },
        onFilterClicked = { viewModel.updateShowFilterBottomSheet(true) },

        listHeading = @Composable {
            Text(
                text = stringResource(R.string.search_results_count_heading, uiState.searchResults.size),
                style = MaterialTheme.typography.labelLarge,
                fontStyle = FontStyle.Italic
            )
        },
        isListEmpty = uiState.searchResults.isEmpty(),
        emptyListInfoTitle = stringResource(R.string.no_search_results_heading),
        emptyListInfoSubtitle = stringResource(R.string.no_search_results_subtitle),

        listScrollState = uiState.resultScrollState
    ) {
        items(items = uiState.searchResults) {
            FoodCard(
                modifier = Modifier.clickable(
                    onClick = {
                        viewModel.addRecentlyViewedFood(it)
                        onFoodCardClicked(it)
                    }
                ),
                type = FoodCardType.BIG,
                image = it.getImage(LocalContext.current),
                title = it.title,
                priceString = it.getPriceString(LocalContext.current),
                isRestaurantFood = it is Meal,
                customizableChips = determineCustomizableChips(
                    LocalContext.current,
                    it,
                    nutritionPreferences
                )
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
                viewModel.performSearch(sharedUiState.searchQuery, it)
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
private fun SearchResultsScreenPreview() {
    NutriChoiceComposeTheme {
        SearchResultsScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            onFoodCardClicked = {},
            nutritionPreferences = NutritionPreferences(
                true,
                true,
                true,
                true,
                true,
                true
            ),
            filterPreferences = FilterPreferences(),

            onShowSnackBar = {}
        )
    }
}