package com.sebiai.nutrichoicecompose.dataclasses

import java.io.Serializable

data class NutritionPreferences(
    val protein: Boolean,
    val carbs: Boolean,
    val fat: Boolean,
    val calories: Boolean,
    val ecoFriendly: Boolean,
    val healthy: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean
) : Serializable {
    constructor() : this(
        false, false, false,
        false, false, false,
        false, false
    )
}
