package com.sebiai.nutrichoicecompose.navigation.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sebiai.nutrichoicecompose.CustomParcelableNavType
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.Data
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.FoodDetailScreen
import com.sebiai.nutrichoicecompose.screens.SettingsScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
internal data class FoodDetailScreenNavRoute(
    val foodId: String,
    val nutritionPreferences: NutritionPreferences
)

fun NavController.navigateToFoodDetailScreen(
    foodId: String,
    nutritionPreferences: NutritionPreferences
) {
    navigate(
        FoodDetailScreenNavRoute(
            foodId,
            nutritionPreferences
        )
    )
}

fun NavGraphBuilder.foodDetailScreenDestination(
    onNavigateToFoodDetailScreen: (AFood, NutritionPreferences) -> Unit
) {
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
            onFoodCardClicked = onNavigateToFoodDetailScreen,
            nutritionPreferences = navRouteObject.nutritionPreferences
        )
    }
}