package com.sebiai.nutrichoicecompose.dataclasses

data class FilterState(
    val highProtein: Boolean,
    val highCarbs: Boolean,
    val lowCarbs: Boolean,
    val lowFat: Boolean,
    val highCalories: Boolean,
    val lowCalories: Boolean,
    val ecoFriendly: Boolean,
    val healthy: Boolean,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val costEfficient: Boolean
) {
    init {
        assert(!lowCarbs || !highCarbs)
        assert(!lowCalories || !highCalories)
    }

    constructor() : this(
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    )
}
