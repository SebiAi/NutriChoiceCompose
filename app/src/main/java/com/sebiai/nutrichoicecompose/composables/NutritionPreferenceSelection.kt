package com.sebiai.nutrichoicecompose.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

enum class Preference {
    PROTEIN, CARBS, FATS, CALORIES, ECO_FRIENDLY, HEALTHY
}

@Composable
fun NutritionPreferenceSelection(
    modifier: Modifier = Modifier,

    nutritionPreferences: NutritionPreferences,
    onPreferenceClicked: (toggledPreference: Preference, newState: Boolean) -> Unit,

    validationErrorText: String,
    validationError: Boolean,
    hideSettingsHint: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp)
        // verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(R.string.preferences_selection_heading),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.preferences_selection_subtitle),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = if (validationError) validationErrorText else "",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.error
            )
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FilterChip(
                            onClick = { onPreferenceClicked(Preference.PROTEIN, !nutritionPreferences.protein) },
                            label = {
                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.preferences_chip_protein)
                                )
                            },
                            selected = nutritionPreferences.protein
                        )
                        FilterChip(
                            onClick = { onPreferenceClicked(Preference.FATS, !nutritionPreferences.fat) },
                            label = {
                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.preferences_chip_fats)
                                )
                            },
                            selected = nutritionPreferences.fat
                        )
                        FilterChip(
                            onClick = { onPreferenceClicked(Preference.ECO_FRIENDLY, !nutritionPreferences.ecoFriendly) },
                            label = {
                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.preferences_chip_eco_friendly)
                                )
                            },
                            selected = nutritionPreferences.ecoFriendly
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FilterChip(
                            onClick = { onPreferenceClicked(Preference.CARBS, !nutritionPreferences.carbs) },
                            label = {
                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.preferences_chip_carbs)
                                )
                            },
                            selected = nutritionPreferences.carbs
                        )
                        FilterChip(
                            onClick = { onPreferenceClicked(Preference.CALORIES, !nutritionPreferences.calories) },
                            label = {
                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.preferences_chip_calories)
                                )
                            },
                            selected = nutritionPreferences.calories
                        )
                        FilterChip(
                            onClick = { onPreferenceClicked(Preference.HEALTHY, !nutritionPreferences.healthy) },
                            label = {
                                Text(
                                    style = MaterialTheme.typography.titleLarge,
                                    text = stringResource(R.string.preferences_chip_healthy)
                                )
                            },
                            selected = nutritionPreferences.healthy
                        )
                    }
                }
            }
            if (!hideSettingsHint) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(R.string.preferences_settings_hint),
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Column(
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
                        onClick = {},
                        label = {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(R.string.preferences_chip_vegetarian)
                            )
                        },
                        selected = nutritionPreferences.vegetarian
                    )
                }
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FilterChip(
                        onClick = {},
                        label = {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(R.string.preferences_chip_vegan)
                            )
                        },
                        selected = nutritionPreferences.vegan
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NutritionPreferenceSelectionValidPreview() {
    NutriChoiceComposeTheme {
        NutritionPreferenceSelection(
            modifier = Modifier.padding(24.dp).fillMaxSize(),

            nutritionPreferences = NutritionPreferences(
                protein = true,
                carbs = false,
                fat = true,
                calories = true,
                ecoFriendly = false,
                healthy = false,
                vegan = true,
                vegetarian = false
            ),
            onPreferenceClicked = {_, _ -> },

            validationErrorText = "Validation error",
            validationError = true,
            hideSettingsHint = false
        )
    }
}