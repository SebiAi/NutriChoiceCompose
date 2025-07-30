package com.sebiai.nutrichoicecompose

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.Data
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.FoodDetailScreen
import com.sebiai.nutrichoicecompose.screens.HomeScreen
import com.sebiai.nutrichoicecompose.screens.SearchResultsScreen
import com.sebiai.nutrichoicecompose.screens.SettingsScreen
import com.sebiai.nutrichoicecompose.screens.viewmodels.HomeAndSearchResultsScreenViewModel
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object HomeNavRoute
@Serializable
object SearchResultsNavRoute
@Serializable
data class SettingsNavRoute(
    val nutritionPreferences: NutritionPreferences
)
@Serializable
data class FoodDetailScreenNavRoute(
    val foodId: String,
    val nutritionPreferences: NutritionPreferences
)

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
    startDestination: Any,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val appState by appViewModel.appState.collectAsStateWithLifecycle()
    val sharedHomeAndSearchResultsScreenViewModel: HomeAndSearchResultsScreenViewModel = viewModel()

    val onFoodCardClicked: (AFood) -> Unit = { food -> navController.navigate(FoodDetailScreenNavRoute(food.id, appState.nutritionPreferences)) }

    NavHost(
        navController,
        startDestination = startDestination,
        modifier
    ) {
        composable<HomeNavRoute> {
            HomeScreen(
                modifier = Modifier.padding(12.dp),
                viewModel = sharedHomeAndSearchResultsScreenViewModel,

                onFoodCardClicked = onFoodCardClicked,
                afterSearchPerformed = { navController.navigate(SearchResultsNavRoute) },
                nutritionPreferences = appState.nutritionPreferences
            )
        }
        composable<SettingsNavRoute>(
            typeMap = mapOf(
                typeOf<NutritionPreferences>() to CustomParcelableNavType(
                    NutritionPreferences::class.java,
                    NutritionPreferences.serializer()
                )
            )
        ) { backStackEntry ->
            val navRouteObject: SettingsNavRoute = backStackEntry.toRoute()

            SettingsScreen(
                modifier = Modifier.padding(12.dp).fillMaxSize(),
                initialNutritionPreferences = navRouteObject.nutritionPreferences,
                onSavePreferences = { nutritionPreferences ->
                    appViewModel.updateNutritionPreferences(nutritionPreferences)
                    navController.popBackStack() // Navigate back to HomeScreen
                }
            )
        }
        composable<SearchResultsNavRoute> {
            SearchResultsScreen(
                modifier = Modifier.padding(12.dp),
                viewModel = sharedHomeAndSearchResultsScreenViewModel,

                onFoodCardClicked = onFoodCardClicked,
                nutritionPreferences = appState.nutritionPreferences
            )
        }
        composable<FoodDetailScreenNavRoute>(
            typeMap = mapOf(
                typeOf<NutritionPreferences>() to CustomParcelableNavType(
                    NutritionPreferences::class.java,
                    NutritionPreferences.serializer()
                )
            )
        ) { backStackEntry ->
            val navRouteObject: FoodDetailScreenNavRoute = backStackEntry.toRoute()

            val food = Data.getFoodById(navRouteObject.foodId)
            FoodDetailScreen(
                food = food!!,
                onFoodCardClicked = { food, nutritionPreferences -> navController.navigate(FoodDetailScreenNavRoute(food.id, nutritionPreferences)) },
                nutritionPreferences = navRouteObject.nutritionPreferences
            )
        }
    }
}