package com.sebiai.nutrichoicecompose.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.sebiai.nutrichoicecompose.R
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

enum class FoodCardType { BIG, SMALL }

// TODO: [NOW] Pull FoodCardBig and FoodCardSmall in here as private

@Composable
fun FoodCard(
    type: FoodCardType,

    image: ImageBitmap,
    title: String,
    priceString: String,
    isRestaurantFood: Boolean,
    customizableChips: List<String>,

    modifier: Modifier = Modifier
) {
    when (type) {
        FoodCardType.BIG -> FoodCardBig(
            image, title, priceString, isRestaurantFood, customizableChips, modifier
        )
        FoodCardType.SMALL -> FoodCardSmall(
            image, title, priceString, customizableChips, modifier
        )
    }
}

fun determineCustomizableChips(context: Context, food: AFood, viewingPreferences: NutritionPreferences): List<String> {
    val result: MutableList<String> = mutableListOf()
    // Always visible
    if (food.vegan) result.add(context.getString(R.string.preferences_chip_vegan))
    if (food.vegetarian) result.add(context.getString(R.string.preferences_chip_vegetarian))

    // Only show if the user specified it in the preferences
    if (food.healthy && viewingPreferences.healthy) result.add(context.getString(R.string.preferences_chip_healthy))
    if (food.ecoFriendly && viewingPreferences.ecoFriendly) result.add(context.getString(R.string.preferences_chip_eco_friendly))

    if (food.highCalories && viewingPreferences.calories) result.add(context.getString(R.string.high_calories))
    if (food.lowCalories && viewingPreferences.calories) result.add(context.getString(R.string.low_calories))

    if (food.highProtein && viewingPreferences.protein) result.add(context.getString(R.string.high_protein))

    if (food.lowFat && viewingPreferences.fat) result.add(context.getString(R.string.low_fat))

    if (food.highCarbs && viewingPreferences.carbs) result.add(context.getString(R.string.high_carbs))
    if (food.lowCarbs && viewingPreferences.carbs) result.add(context.getString(R.string.low_carbs))

    return result
}

@Preview
@Composable
private fun FoodCardBigPreview() {
    NutriChoiceComposeTheme {
        FoodCard(
            type = FoodCardType.BIG,

            image = ImageBitmap.imageResource(R.drawable.tomato_paste_image),
            title = "Title",
            priceString = "$$$",
            isRestaurantFood = false,
            customizableChips = listOf("ChipA", "ChipB")
        )
    }
}
@Preview
@Composable
private fun FoodCardSmallPreview() {
    NutriChoiceComposeTheme {
        FoodCard(
            type = FoodCardType.SMALL,

            image = ImageBitmap.imageResource(R.drawable.tomato_paste_image),
            title = "Title",
            priceString = "$$$",
            isRestaurantFood = false,
            customizableChips = listOf("ChipA", "ChipB")
        )
    }
}