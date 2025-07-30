package com.sebiai.nutrichoicecompose.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class FilterPreferences(
    val vegan: Boolean,
    val vegetarian: Boolean
) : Parcelable {
    constructor() : this(
        false, false
    )
}