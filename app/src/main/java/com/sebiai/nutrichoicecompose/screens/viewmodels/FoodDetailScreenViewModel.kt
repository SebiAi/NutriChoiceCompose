package com.sebiai.nutrichoicecompose.screens.viewmodels

import androidx.compose.foundation.ScrollState
import androidx.lifecycle.ViewModel
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FoodDetailScreenUiState(
    val food: AFood,

    val scrollState: ScrollState = ScrollState(0)
)

class FoodDetailScreenViewModel(
    food: AFood
) : ViewModel() {
    /*
     * Screen UI state
     */
    private val _uiState = MutableStateFlow(
        FoodDetailScreenUiState(food)
    )
    val uiState: StateFlow<FoodDetailScreenUiState> = _uiState.asStateFlow()

    /*
     * Business logic
     */
    fun updateFood(newFood: AFood) {
        _uiState.update { currentState ->
            currentState.copy(
                food = newFood
            )
        }
    }
}