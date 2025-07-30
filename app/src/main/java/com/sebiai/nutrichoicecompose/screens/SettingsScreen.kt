package com.sebiai.nutrichoicecompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.composables.NutritionPreferenceSelection
import com.sebiai.nutrichoicecompose.composables.Preference
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,

    initialNutritionPreferences: NutritionPreferences,
    onSavePreferences: (NutritionPreferences) -> Unit
) {
    var nutritionValues by rememberSaveable { mutableStateOf(initialNutritionPreferences) }
    val isSelectionValid: Boolean = nutritionValues.countTrueBooleans() <= 3

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NutritionPreferenceSelection(
            nutritionPreferences = nutritionValues,
            onPreferenceClicked = { preference, newState ->
                nutritionValues = when (preference) {
                    Preference.PROTEIN -> nutritionValues.copy(protein = newState)
                    Preference.CARBS -> nutritionValues.copy(carbs = newState)
                    Preference.FATS -> nutritionValues.copy(fat = newState)
                    Preference.CALORIES -> nutritionValues.copy(calories = newState)
                    Preference.ECO_FRIENDLY -> nutritionValues.copy(ecoFriendly = newState)
                    Preference.HEALTHY -> nutritionValues.copy(healthy = newState)
                    Preference.VEGETARIAN -> nutritionValues.copy(vegetarian = newState)
                    Preference.VEGAN -> nutritionValues.copy(vegan = newState)
                }
            },

            validationErrorText = stringResource(R.string.preferences_validation_error),
            validationError = !isSelectionValid,
            hideSettingsHint = true
        )
        Button(
            enabled = isSelectionValid,
            onClick = { onSavePreferences(nutritionValues) }
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
            onSavePreferences = {}
        )
    }
}