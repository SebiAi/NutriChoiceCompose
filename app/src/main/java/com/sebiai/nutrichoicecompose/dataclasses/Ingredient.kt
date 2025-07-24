package com.sebiai.nutrichoicecompose.dataclasses

import androidx.annotation.DrawableRes

class Ingredient(
    title: String,
    @DrawableRes
    imageResource: Int,
    nutritionValues: NutritionValues,
    price: Price,
    nutriScore: Score,
    greenScore: Score,
    dietaryPreferences: DietaryPreferences,
): AFood(title, imageResource, nutritionValues, price, nutriScore, greenScore, dietaryPreferences)