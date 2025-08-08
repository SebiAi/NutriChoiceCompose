package com.sebiai.nutrichoicecompose.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sebiai.nutrichoicecompose.AppViewModel
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.navigation.routes.FoodDetailScreenNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.HomeNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.OnboardingNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.SearchResultsNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.SettingsNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.foodDetailScreenDestination
import com.sebiai.nutrichoicecompose.navigation.routes.homeScreenDestination
import com.sebiai.nutrichoicecompose.navigation.routes.navigateToFoodDetailScreen
import com.sebiai.nutrichoicecompose.navigation.routes.navigateToHomeScreen
import com.sebiai.nutrichoicecompose.navigation.routes.navigateToHomeScreenWithPopUp
import com.sebiai.nutrichoicecompose.navigation.routes.navigateToSearchResultsScreen
import com.sebiai.nutrichoicecompose.navigation.routes.onboardingScreenDestination
import com.sebiai.nutrichoicecompose.navigation.routes.searchResultsScreenDestination
import com.sebiai.nutrichoicecompose.navigation.routes.settingsScreenDestination
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel

fun getTitleForCurrentRoute(context: Context, route: String): String {
    // routes have the qualifiedName of the class plus a url like arguments
    // when a data class is used
    val routeQualifiedName = route.substringBefore('/')
    val titleRes = when (routeQualifiedName) {
        HomeNavRoute::class.qualifiedName!! -> R.string.app_name
        SettingsNavRoute::class.qualifiedName!! -> R.string.settings_screen_title
        SearchResultsNavRoute::class.qualifiedName!! -> R.string.search_results_screen_title
        FoodDetailScreenNavRoute::class.qualifiedName!! -> R.string.food_detail_screen_title
        else -> null
    }
    return titleRes?.let { context.getString(titleRes) }?: ""
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    appViewModel: AppViewModel,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    val appState by appViewModel.appState.collectAsStateWithLifecycle()
    val sharedHomeAndSearchResultsScreenViewModel: HomeAndSearchResultsScreenViewModel = viewModel()

    val onFoodCardClicked: (AFood) -> Unit = { food -> navController.navigateToFoodDetailScreen(food.id, appState.nutritionPreferences) }

    NavHost(
        navController,
        startDestination = startDestination,
        modifier
    ) {
        onboardingScreenDestination(
            onOnboardingComplete = { nutritionPreferences, filterPreferences ->
                appViewModel.completeOnboarding(nutritionPreferences, filterPreferences)
                navController.navigateToHomeScreenWithPopUp(OnboardingNavRoute)
            }
        )

        homeScreenDestination(
            sharedHomeAndSearchResultsScreenViewModel = sharedHomeAndSearchResultsScreenViewModel,

            onFoodCardClicked = onFoodCardClicked,
            onNavSearchResults = { navController.navigateToSearchResultsScreen() },
            nutritionPreferences = appState.nutritionPreferences,
            filterPreferences = appState.filterPreferences,

            onShowSnackBar = appViewModel::showSnackBar
        )
        settingsScreenDestination(
            onUpdatePreferences = { nutritionPreferences, filterPreferences ->
                appViewModel.updateNutritionPreferences(nutritionPreferences)
                appViewModel.updateFilterPreferences(filterPreferences)
            },
            onSettingsScreenClose = { navController.popBackStack() },

            onShowSnackBar = { appViewModel.showSnackBar(navController.context.getString(it)) }
        )
        searchResultsScreenDestination(
            sharedHomeAndSearchResultsScreenViewModel = sharedHomeAndSearchResultsScreenViewModel,

            onFoodCardClicked = onFoodCardClicked,
            nutritionPreferences = appState.nutritionPreferences,
            filterPreferences = appState.filterPreferences,

            onShowSnackBar = appViewModel::showSnackBar
        )
        foodDetailScreenDestination(
            onNavigateToFoodDetailScreen = { food, nutritionPreferences ->
                navController.navigateToFoodDetailScreen(
                    foodId = food.id,
                    nutritionPreferences = nutritionPreferences
                )
            }
        )
    }
}