package com.sebiai.nutrichoicecompose.screens

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.composables.FilterSearchBar
import com.sebiai.nutrichoicecompose.composables.FoodCardBig
import com.sebiai.nutrichoicecompose.dataclasses.Data
import com.sebiai.nutrichoicecompose.dataclasses.Meal
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralSearchScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // TODO: [Now + 1] Move to ViewModel bc. Ingredient class fails to pack into parcel
        var searchQuery by rememberSaveable { mutableStateOf("") }
        var searchResults by rememberSaveable { mutableStateOf(listOf(Data.search("schn")[0])) }

        val searchBarOnSearch: (String) -> Unit = { query: String ->
            Log.d(null, "Searched with query \"$query\"")
        }

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
            query = searchQuery,
            onSearch = searchBarOnSearch,
            onQueryChanged = { searchQuery = it },
            onClearQuery = { searchQuery = "" },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = searchResults) {
                FoodCardBig(
                    image = it.getImage(LocalContext.current),
                    title = it.title,
                    priceString = it.getPriceString(LocalContext.current),
                    isRestaurantFood = it is Meal,
                    customizableChips = listOf() // TODO: [NOW + 2] Implement chips
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GeneralSearchScreenPreview() {
    NutriChoiceComposeTheme {
        GeneralSearchScreen(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp))
    }
}