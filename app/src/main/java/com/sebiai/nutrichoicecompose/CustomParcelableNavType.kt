package com.sebiai.nutrichoicecompose

import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * Can be used in typeMap to pass non standard supported types to other
 * routes.
 *
 * @param T A type that implements Parcelable
 * @property clazz The class of the type T
 * @property serializer The serializer of the class
 * @constructor Creates a NavType<T> via json serialization
 */
class CustomParcelableNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>
) : NavType<T>(isNullableAllowed = false) {
    override val name: String = clazz.name

    override fun put(
        bundle: SavedState,
        key: String,
        value: T
    ) {
        bundle.putParcelable(key, value)
    }
    override fun get(
        bundle: SavedState,
        key: String
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable<T>(key,  clazz)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable<T>(key)
        }
    }

    override fun parseValue(value: String): T { return Json.decodeFromString(serializer, value) }
    override fun serializeAsValue(value: T): String { return Json.encodeToString(serializer, value) }
}