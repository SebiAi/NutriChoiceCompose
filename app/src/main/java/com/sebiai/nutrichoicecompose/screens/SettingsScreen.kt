package com.sebiai.nutrichoicecompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.composables.FilterPreference
import com.sebiai.nutrichoicecompose.composables.FilterPreferenceSelection
import com.sebiai.nutrichoicecompose.composables.NutritionPreferenceSelection
import com.sebiai.nutrichoicecompose.composables.NutritionPreference
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,

    initialNutritionPreferences: NutritionPreferences,
    initialFilterPreferences: FilterPreferences,
    onSavePreferences: (NutritionPreferences, FilterPreferences) -> Unit
) {
    var nutritionValues by rememberSaveable { mutableStateOf(initialNutritionPreferences) }
    var filterPreferences by rememberSaveable { mutableStateOf(initialFilterPreferences) }
    val isSelectionValid: Boolean = nutritionValues.countTrueBooleans() <= 3

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            NutritionPreferenceSelection(
                nutritionPreferences = nutritionValues,
                onPreferenceClicked = { preference, newState ->
                    nutritionValues = when (preference) {
                        NutritionPreference.PROTEIN -> nutritionValues.copy(protein = newState)
                        NutritionPreference.CARBS -> nutritionValues.copy(carbs = newState)
                        NutritionPreference.FATS -> nutritionValues.copy(fat = newState)
                        NutritionPreference.CALORIES -> nutritionValues.copy(calories = newState)
                        NutritionPreference.ECO_FRIENDLY -> nutritionValues.copy(ecoFriendly = newState)
                        NutritionPreference.HEALTHY -> nutritionValues.copy(healthy = newState)
                    }
                },

                validationErrorText = stringResource(R.string.preferences_validation_error),
                validationError = !isSelectionValid,
                hideSettingsHint = true
            )
            Spacer(modifier = Modifier.height(28.dp))
            FilterPreferenceSelection(
                filterPreferences = filterPreferences,
                onPreferenceClicked = { preference, newState ->
                    filterPreferences = when (preference) {
                        FilterPreference.VEGETARIAN -> filterPreferences.copy(vegetarian = newState)
                        FilterPreference.VEGAN -> filterPreferences.copy(vegan = newState)
                    }
                }
            )
        }
        Button(
            enabled = isSelectionValid && nutritionValues.countTrueBooleans() >= 1,
            onClick = { onSavePreferences(nutritionValues, filterPreferences) }
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.done_confirmation),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    NutriChoiceComposeTheme {
        SettingsScreen(
            modifier = Modifier.fillMaxSize(),
            initialNutritionPreferences = NutritionPreferences(),
            initialFilterPreferences = FilterPreferences(),
            onSavePreferences = { _, _ -> }
        )
    }
}