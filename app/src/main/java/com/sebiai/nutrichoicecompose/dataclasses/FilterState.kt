package com.sebiai.nutrichoicecompose.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class FilterState(
    val highProtein: Boolean,
    val lowFat: Boolean,
    val ecoFriendly: Boolean,
    val healthy: Boolean,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val costEfficient: Boolean,
    val carbs: ThreeStateFilterState,
    val calories: ThreeStateFilterState
) : Parcelable {
    constructor() : this(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        ThreeStateFilterState.NEUTRAL,
        ThreeStateFilterState.NEUTRAL
    )
    constructor(filterPreferences: FilterPreferences) : this(
        highProtein = false,
        lowFat = false,
        ecoFriendly = false,
        healthy = false,
        vegetarian = filterPreferences.vegetarian,
        vegan = filterPreferences.vegan,
        costEfficient = false,
        carbs = ThreeStateFilterState.NEUTRAL,
        calories = ThreeStateFilterState.NEUTRAL
    )

    @Serializable
    @Parcelize
    enum class ThreeStateFilterState : Parcelable {
        HIGH, NEUTRAL, LOW
    }
}
