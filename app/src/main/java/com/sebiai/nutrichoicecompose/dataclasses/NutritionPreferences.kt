package com.sebiai.nutrichoicecompose.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class NutritionPreferences(
    val protein: Boolean,
    val carbs: Boolean,
    val fat: Boolean,
    val calories: Boolean,
    val ecoFriendly: Boolean,
    val healthy: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean
) : Parcelable {
    constructor() : this(
        false, false, false,
        false, false, false,
        false, false
    )
}
