package com.sebiai.nutrichoicecompose

import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import com.sebiai.nutrichoicecompose.dataclasses.Data
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.FoodDetailScreen
import com.sebiai.nutrichoicecompose.screens.GeneralSearchScreen
import com.sebiai.nutrichoicecompose.screens.SettingsScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
object GeneralSearchNavRoute
@Serializable
object SettingsNavRoute
@Serializable
data class FoodDetailScreenNavRoute(
    val foodId: String,
    val nutritionPreferences: NutritionPreferences
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination,
        modifier
    ) {
        composable<GeneralSearchNavRoute> {
            GeneralSearchScreen(
                modifier = Modifier.padding(12.dp),
                onFoodCardClicked = { food, nutritionPreferences -> navController.navigate(FoodDetailScreenNavRoute(food.id, nutritionPreferences)) }
            )
        }
        composable<SettingsNavRoute> {
            SettingsScreen()
        }
        composable<FoodDetailScreenNavRoute>(
            typeMap = mapOf(
                typeOf<NutritionPreferences>() to object : NavType<NutritionPreferences>(isNullableAllowed = false) {
                    override fun put(
                        bundle: SavedState,
                        key: String,
                        value: NutritionPreferences
                    ) {
                        bundle.putParcelable(key, value)
                    }
                    override fun get(
                        bundle: SavedState,
                        key: String
                    ): NutritionPreferences? {
                        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            bundle.getParcelable(key, NutritionPreferences::class.java)
                        } else {
                            @Suppress("DEPRECATION")
                            bundle.getParcelable(key)
                        }
                    }

                    override fun parseValue(value: String): NutritionPreferences {
                        return Json.decodeFromString(value)
                    }
                    override fun serializeAsValue(value: NutritionPreferences): String {
                        return Json.encodeToString(value)
                    }
                }
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