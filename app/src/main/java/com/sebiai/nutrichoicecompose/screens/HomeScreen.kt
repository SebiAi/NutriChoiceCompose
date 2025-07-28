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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeScreenViewModel
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFoodCardClicked: (AFood, NutritionPreferences) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.welcome_greeting),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Start searching for ingredients, meals or restaurants.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        FilterSearchBar(
            modifier = Modifier.padding(0.dp, 12.dp),
            query = uiState.searchQuery,
            onSearch = { query: String ->
                Log.d(null, "Searched with query \"$query\"")
                viewModel.performSearch(query, uiState.filterState)
                uiState.resultScrollState.requestScrollToItem(0)
            },
            onQueryChanged = viewModel::updateQuery,
            onClearQuery = { viewModel.updateQuery("") },
            onFilterClicked = {},
            hint = "Start searching"
        )

        Text(
            text = "Recently Viewed",
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
                        onClick = { onFoodCardClicked(it, uiState.nutritionPreferences) }
                    ),
                    type = FoodCardType.BIG,
                    image = it.getImage(LocalContext.current),
                    title = it.title,
                    priceString = it.getPriceString(LocalContext.current),
                    isRestaurantFood = it is Meal,
                    customizableChips = determineCustomizableChips(LocalContext.current, it, uiState.nutritionPreferences)
                )
            }
        }
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
            onFoodCardClicked = {food, nutritionPreferences -> }
        )
    }
}