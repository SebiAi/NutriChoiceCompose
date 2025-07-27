package com.sebiai.nutrichoicecompose.dataclasses

import java.io.Serializable

data class NutritionValues(
    val calories: Double,
    val protein: Double,
    val fat: Double,
    val carbs: Double,
    val salt: Double,
    val sugar: Double,
) : Serializable {

    init {
        assert(calories >= 0.0)
        assert(protein >= 0.0)
        assert(fat >= 0.0)
        assert(carbs >= 0.0)
        assert(salt >= 0.0)
        assert(sugar >= 0.0)
    }

    constructor() : this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}
