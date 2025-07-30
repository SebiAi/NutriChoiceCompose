package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

enum class FilterPreference {
    VEGETARIAN, VEGAN
}

@Composable
fun FilterPreferenceSelection(
    modifier: Modifier = Modifier,

    filterPreferences: FilterPreferences,
    onPreferenceClicked: (toggledPreference: FilterPreference, newState: Boolean) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(R.string.preferences_dietary_header),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.padding(16.dp, 0.dp),
        ) {
            Column(
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilterChip(
                    onClick = { onPreferenceClicked(FilterPreference.VEGETARIAN, !filterPreferences.vegetarian) },
                    label = {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = stringResource(R.string.preferences_chip_vegetarian)
                        )
                    },
                    selected = filterPreferences.vegetarian
                )
            }
            Column(
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilterChip(
                    onClick = { onPreferenceClicked(FilterPreference.VEGAN, !filterPreferences.vegan) },
                    label = {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = stringResource(R.string.preferences_chip_vegan)
                        )
                    },
                    selected = filterPreferences.vegan
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NutritionPreferenceSelectionValidPreview() {
    NutriChoiceComposeTheme {
        FilterPreferenceSelection(
            modifier = Modifier.padding(24.dp).fillMaxSize(),

            filterPreferences = FilterPreferences(
                vegan = true,
                vegetarian = false
            ),
            onPreferenceClicked = {_, _ -> }
        )
    }
}