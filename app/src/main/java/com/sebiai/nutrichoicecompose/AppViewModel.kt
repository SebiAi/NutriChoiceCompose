package com.sebiai.nutrichoicecompose

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebiai.nutrichoicecompose.dataclasses.FilterPreferences
import com.sebiai.nutrichoicecompose.dataclasses.NutritionPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AppState(
    val nutritionPreferences: NutritionPreferences = NutritionPreferences(),
    val filterPreferences: FilterPreferences = FilterPreferences(),

    val snackBarHostState: SnackbarHostState = SnackbarHostState()
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

    fun updateFilterPreferences(newFilterPreferences: FilterPreferences) {
        _appState.update { currentState ->
            currentState.copy(
                filterPreferences = newFilterPreferences
            )
        }
    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            _appState.value.snackBarHostState.showSnackbar(message)
        }
    }
}