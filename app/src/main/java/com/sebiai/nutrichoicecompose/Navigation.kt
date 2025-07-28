package com.sebiai.nutrichoicecompose

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
object SettingsNavRoute
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
    modifier: Modifier = Modifier
) {
    val onFoodCardClicked: (AFood, NutritionPreferences) -> Unit = { food, nutritionPreferences -> navController.navigate(FoodDetailScreenNavRoute(food.id, nutritionPreferences)) }

    val sharedHomeAndSearchResultsScreenViewModel: HomeAndSearchResultsScreenViewModel = viewModel()

    NavHost(
        navController,
        startDestination = startDestination,
        modifier
    ) {
        composable<HomeNavRoute> {
            HomeScreen(
                modifier = Modifier.padding(12.dp),
                onFoodCardClicked = onFoodCardClicked,
                afterSearchPerformed = { navController.navigate(SearchResultsNavRoute) },
                viewModel = sharedHomeAndSearchResultsScreenViewModel
            )
        }
        composable<SettingsNavRoute> {
            SettingsScreen()
        }
        composable<SearchResultsNavRoute> {
            SearchResultsScreen(
                modifier = Modifier.padding(12.dp),
                onFoodCardClicked = onFoodCardClicked,
                viewModel = sharedHomeAndSearchResultsScreenViewModel
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