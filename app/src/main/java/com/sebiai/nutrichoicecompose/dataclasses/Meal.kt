package com.sebiai.nutrichoicecompose.dataclasses

import androidx.annotation.DrawableRes

class Meal(
    title: String,
    @DrawableRes
    imageResource: Int,
    nutritionValues: NutritionValues,
    price: Price,
    nutriScore: Score,
    greenScore: Score,
    dietaryPreferences: DietaryPreferences,
    val ingredients: List<AFood>,
    private val restaurant: Mensa
): AFood(title, imageResource, nutritionValues, price, nutriScore, greenScore, dietaryPreferences) {
    override val searchString: String
        get() = super.searchString + "\n" + restaurant.name

    val restaurantName: String
        get() = restaurant.name
}
