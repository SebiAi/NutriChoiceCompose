package com.sebiai.nutrichoicecompose.dataclasses

import java.io.Serializable

data class NutritionValues(
    val calories: Double,
    val protein: Double,
    val fat: Double,
    val carbs: Double,
    val salt: Double,
    val sugar: Double,
) : Serializable
