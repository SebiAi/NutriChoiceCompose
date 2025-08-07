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
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.screens.SettingsScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
internal data class SettingsNavRoute(
    val nutritionPreferences: NutritionPreferences,
    val filterPreferences: FilterPreferences
)

fun NavController.navigateToSettingsScreen(
    nutritionPreferences: NutritionPreferences,
    filterPreferences: FilterPreferences
) {
    navigate(
        SettingsNavRoute(
            nutritionPreferences,
            filterPreferences
        )
    )
}

/**
 * @param onShowSnackBar Int Parameter must be treated as a string resource.
 */
fun NavGraphBuilder.settingsScreenDestination(
    onUpdatePreferences: (NutritionPreferences, FilterPreferences) -> Unit,
    onSettingsScreenClose: () -> Unit,

    onShowSnackBar: (Int) -> Unit
) {
    composable<SettingsNavRoute>(
        typeMap = mapOf(
            typeOf<NutritionPreferences>() to CustomParcelableNavType(
                NutritionPreferences::class.java,
                NutritionPreferences.serializer()
            ),
            typeOf<FilterPreferences>() to CustomParcelableNavType(
                FilterPreferences::class.java,
                FilterPreferences.serializer()
            )
        )
    ) { backStackEntry ->
        val navRouteObject: SettingsNavRoute = backStackEntry.toRoute()

        SettingsScreen(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            initialNutritionPreferences = navRouteObject.nutritionPreferences,
            initialFilterPreferences = navRouteObject.filterPreferences,
            onSavePreferences = { nutritionPreferences, filterPreferences ->
                onUpdatePreferences(nutritionPreferences, filterPreferences)

                onSettingsScreenClose()
                onShowSnackBar(R.string.preferences_saved)
            }
        )
    }
}