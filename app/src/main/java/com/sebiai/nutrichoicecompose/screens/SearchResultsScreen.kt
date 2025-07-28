package com.sebiai.nutrichoicecompose.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.sebiai.nutrichoicecompose.composables.FilterSearchBar
import com.sebiai.nutrichoicecompose.composables.FoodCard
import com.sebiai.nutrichoicecompose.composables.FoodCardType
import com.sebiai.nutrichoicecompose.composables.determineCustomizableChips
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.Meal
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    onFoodCardClicked: (AFood, NutritionPreferences) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeAndSearchResultsScreenViewModel = viewModel()
) {
    val uiState by viewModel.searchAndResultsScreenUiState.collectAsStateWithLifecycle()
    val sharedUiState by viewModel.sharedSearchFunctionUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        FilterSearchBar(
            query = sharedUiState.searchQuery,
            onSearch = { query: String ->
                Log.d(null, "Searched with query \"$query\"")
                viewModel.performSearch(query, sharedUiState.filterState)
                uiState.resultScrollState.requestScrollToItem(0)
            },
            onQueryChanged = viewModel::updateSearchQuery,
            onClearQuery = { viewModel.updateSearchQuery("") },
            onFilterClicked = {},
            hint = stringResource(R.string.search_bar_hint)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.search_results_count_heading, uiState.searchResults.size),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = uiState.resultScrollState
        ) {
            items(items = uiState.searchResults) {
                FoodCard(
                    modifier = Modifier.clickable(
                        onClick = { onFoodCardClicked(it, sharedUiState.nutritionPreferences) }
                    ),
                    type = FoodCardType.BIG,
                    image = it.getImage(LocalContext.current),
                    title = it.title,
                    priceString = it.getPriceString(LocalContext.current),
                    isRestaurantFood = it is Meal,
                    customizableChips = determineCustomizableChips(LocalContext.current, it, sharedUiState.nutritionPreferences)
                )
            }
        }
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
            onFoodCardClicked = {food, nutritionPreferences -> }
        )
    }
}