package com.sebiai.nutrichoicecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AppState(
    val nutritionPreferences: NutritionPreferences = NutritionPreferences()
)

class AppViewModel : ViewModel() {
    /**
     * State
     */
    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    /*
     * Business logic
     */
    fun updateNutritionPreferences(newNutritionPreferences: NutritionPreferences) {
        _appState.update { currentState ->
            currentState.copy(
                nutritionPreferences = newNutritionPreferences
            )
        }
    }
}