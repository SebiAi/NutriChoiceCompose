package com.sebiai.nutrichoicecompose.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.composables.FilterPreference
import com.sebiai.nutrichoicecompose.composables.FilterPreferenceSelection
import com.sebiai.nutrichoicecompose.composables.HorizontalCircularPagerIndicator
import com.sebiai.nutrichoicecompose.composables.NutritionPreference
import com.sebiai.nutrichoicecompose.composables.NutritionPreferenceSelection
import com.sebiai.nutrichoicecompose.composables.QRScannerFloatingActionButton
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onOnboardingComplete: (NutritionPreferences, FilterPreferences) -> Unit,
    
    initialPage: Int = 0
) {
    var nutritionPreferences by rememberSaveable { mutableStateOf(NutritionPreferences()) }
    var filterPreferences by rememberSaveable { mutableStateOf(FilterPreferences()) }
    val isSelectionValid: Boolean = nutritionPreferences.countTrueBooleans() <= 3

    val pageContents = listOf(
        @Composable {
            FirstPage()
        },
        @Composable {
            SecondPage()
        },
        @Composable {
            ThirdPage(
                nutritionPreferences = nutritionPreferences,
                filterPreferences = filterPreferences,
                onNutritionPreferenceClicked = { preference, newState ->
                    nutritionPreferences = when (preference) {
                        NutritionPreference.PROTEIN -> nutritionPreferences.copy(protein = newState)
                        NutritionPreference.CARBS -> nutritionPreferences.copy(carbs = newState)
                        NutritionPreference.FATS -> nutritionPreferences.copy(fat = newState)
                        NutritionPreference.CALORIES -> nutritionPreferences.copy(calories = newState)
                        NutritionPreference.ECO_FRIENDLY -> nutritionPreferences.copy(ecoFriendly = newState)
                        NutritionPreference.HEALTHY -> nutritionPreferences.copy(healthy = newState)
                    }
                },
                onFilterPreferenceClicked = { preference, newState ->
                    filterPreferences = when (preference) {
                        FilterPreference.VEGETARIAN -> filterPreferences.copy(vegetarian = newState)
                        FilterPreference.VEGAN -> filterPreferences.copy(vegan = newState)
                    }
                },
                validationError = !isSelectionValid
            )
        },
    )
    val pagerState = rememberPagerState(pageCount = pageContents::size, initialPage = initialPage)
    val scope = rememberCoroutineScope()
    val showDoneButton = pagerState.currentPage + 1 >= pagerState.pageCount

    BackHandler(
        enabled = pagerState.currentPage > 0
    ) {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1F),
            state = pagerState
        ) { index ->
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                pageContents[index]()
            }
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 12.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalCircularPagerIndicator(
                pagerState = pagerState,
                indicatorSize = 10.dp
            )
            AnimatedContent(
                modifier = Modifier.align(Alignment.BottomEnd),
                targetState = showDoneButton
            ) { targetState ->
                when(targetState) {
                    true -> {
                        Button(
                            modifier = Modifier.padding(end = 12.dp),
                            onClick = { onOnboardingComplete(nutritionPreferences, filterPreferences) },
                            enabled = isSelectionValid && nutritionPreferences.countTrueBooleans() >= 1
                        ) {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(R.string.done_confirmation),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    false -> {
                        FilledIconButton(
                            modifier = Modifier.padding(end = 12.dp).size(56.dp),
                            onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FirstPage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier.weight(0.8F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.onboarding_welcome_to),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displaySmall,
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            modifier = Modifier.weight(1F),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(R.string.onboarding_app_description),
            textAlign = TextAlign.Center
        )
    }
}
@Composable
private fun SecondPage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier.weight(0.5F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.onboarding_introducing_our),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayMedium,
                text = stringResource(R.string.onboarding_qr_scanner_introduction_heading),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QRScannerFloatingActionButton(
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.onboarding_qr_scanner_description),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
private fun ThirdPage(
    nutritionPreferences: NutritionPreferences,
    filterPreferences: FilterPreferences,

    onNutritionPreferenceClicked: (NutritionPreference, Boolean) -> Unit,
    onFilterPreferenceClicked: (FilterPreference, Boolean) -> Unit,
    validationError: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NutritionPreferenceSelection(
            nutritionPreferences = nutritionPreferences,
            onPreferenceClicked = onNutritionPreferenceClicked,

            validationErrorText = stringResource(R.string.preferences_validation_error),
            validationError = validationError,
            hideSettingsHint = false
        )
        Spacer(modifier = Modifier.height(28.dp))
        FilterPreferenceSelection(
            filterPreferences = filterPreferences,
            onPreferenceClicked = onFilterPreferenceClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenFirstPagePreview() {
    NutriChoiceComposeTheme {
        OnboardingScreen(
            modifier = Modifier.fillMaxSize(),
            onOnboardingComplete = { _, _ -> },

            initialPage = 0
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun OnboardingScreenSecondPagePreview() {
    NutriChoiceComposeTheme {
        OnboardingScreen(
            modifier = Modifier.fillMaxSize(),
            onOnboardingComplete = { _, _ -> },

            initialPage = 1
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun OnboardingScreenThirdPagePreview() {
    NutriChoiceComposeTheme {
        OnboardingScreen(
            modifier = Modifier.fillMaxSize(),
            onOnboardingComplete = { _, _ -> },

            initialPage = 2
        )
    }
}