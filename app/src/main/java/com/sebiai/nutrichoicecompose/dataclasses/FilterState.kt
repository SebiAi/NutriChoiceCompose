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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilterState

        if (highProtein != other.highProtein) return false
        if (lowFat != other.lowFat) return false
        if (ecoFriendly != other.ecoFriendly) return false
        if (healthy != other.healthy) return false
        if (vegetarian != other.vegetarian) return false
        if (vegan != other.vegan) return false
        if (costEfficient != other.costEfficient) return false
        if (carbs != other.carbs) return false
        if (calories != other.calories) return false

        return true
    }

    override fun hashCode(): Int {
        var result = highProtein.hashCode()
        result = 31 * result + lowFat.hashCode()
        result = 31 * result + ecoFriendly.hashCode()
        result = 31 * result + healthy.hashCode()
        result = 31 * result + vegetarian.hashCode()
        result = 31 * result + vegan.hashCode()
        result = 31 * result + costEfficient.hashCode()
        result = 31 * result + carbs.hashCode()
        result = 31 * result + calories.hashCode()
        return result
    }


}
