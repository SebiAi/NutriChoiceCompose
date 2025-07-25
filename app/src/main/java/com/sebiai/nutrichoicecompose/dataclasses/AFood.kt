package com.sebiai.nutrichoicecompose.dataclasses

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import com.sebiai.nutrichoicecompose.R
import java.io.Serializable

abstract class AFood(
    val title: String,
    @field:DrawableRes
    val imageResource: Int,
    private val nutritionValues: NutritionValues,
    private val price: Price,

    val nutriScore: Score,
    val greenScore: Score,

    val dietaryPreferences: DietaryPreferences
) : Serializable {
    enum class Price { LOW, MEDIUM, HIGH }
    enum class Score { A, B, C, D, E, NA }
    enum class DietaryPreferences { NONE, VEGAN, VEGETARIAN }

    open val searchString: String
        get() = title

    val highProtein: Boolean get() = nutritionValues.protein >= 20
    val highCalories: Boolean get() = nutritionValues.calories >= 300
    val lowCalories: Boolean get() = nutritionValues.calories < 50
    val highCarbs: Boolean get() = nutritionValues.carbs >= 28
    val lowCarbs: Boolean get() = nutritionValues.carbs <= 10
    val lowFat: Boolean get() = nutritionValues.fat <= 3
    val vegan: Boolean get() = dietaryPreferences == DietaryPreferences.VEGAN
    val vegetarian: Boolean get() = dietaryPreferences == DietaryPreferences.VEGAN || dietaryPreferences == DietaryPreferences.VEGETARIAN
    val ecoFriendly: Boolean get() = greenScore == Score.B || greenScore == Score.A
    val healthy: Boolean get() = nutriScore == Score.B || nutriScore == Score.A
    val costEffective: Boolean get() = price == Price.LOW

    fun getPriceString(context: Context): String {
        val priceSymbol = context.getString(R.string.price_symbol)
        val pricePlaceholderSymbol = context.getString(R.string.price_placeholder_symbol)

        return when (price) {
            Price.LOW -> priceSymbol.repeat(1) + pricePlaceholderSymbol.repeat(2)
            Price.MEDIUM -> priceSymbol.repeat(2) + pricePlaceholderSymbol.repeat(1)
            Price.HIGH -> priceSymbol.repeat(3)
        }
    }

    fun getImage(context: Context): ImageBitmap {
        return ImageBitmap.imageResource(context.resources, imageResource)
    }

    fun getNutriScoreImage(context: Context): ImageVector {
        @DrawableRes
        val nutriScoreResource = when (nutriScore) {
            Score.A -> R.drawable.nutriscore_a_new_en
            Score.B -> R.drawable.nutriscore_b_new_en
            Score.C -> R.drawable.nutriscore_c_new_en
            Score.D -> R.drawable.nutriscore_d_new_en
            Score.E -> R.drawable.nutriscore_e_new_en
            Score.NA -> R.drawable.nutriscore_unknown_new_en
        }

        return ImageVector.vectorResource(context.theme,  context.resources, nutriScoreResource)
    }
    fun getGreenScoreImage(context: Context): ImageVector {
        @DrawableRes
        val greenScoreResource = when (greenScore) {
            Score.A -> R.drawable.green_score_a
            Score.B -> R.drawable.green_score_b
            Score.C -> R.drawable.green_score_c
            Score.D -> R.drawable.green_score_d
            Score.E -> R.drawable.green_score_e
            Score.NA -> R.drawable.green_score_not_applicable
        }

        return ImageVector.vectorResource(context.theme,  context.resources, greenScoreResource)
    }
}
