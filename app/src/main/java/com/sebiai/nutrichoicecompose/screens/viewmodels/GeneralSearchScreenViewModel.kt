package com.sebiai.nutrichoicecompose.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.Data
import com.sebiai.nutrichoicecompose.dataclasses.FilterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO: [NOW] Use Saved State module for ViewModel to keep the data when a system-initiated process death occurs: https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate

data class GeneralSearchScreenUiState(
    val searchQuery: String = "",
    val searchResults: List<AFood> = listOf(Data.search("schn", FilterState())[0]),
    val filterState: FilterState = FilterState()
)

class GeneralSearchScreenViewModel : ViewModel() {
    /*
     * Screen UI state
     */
    private val _uiState = MutableStateFlow(GeneralSearchScreenUiState())
    val uiState: StateFlow<GeneralSearchScreenUiState> = _uiState.asStateFlow()

    /*
     * Business logic
     */
    fun updateQuery(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = newQuery
            )
        }
    }

    fun performSearch(query: String, filters: FilterState) {
        _uiState.update { currentState ->
            currentState.copy(
                searchResults = Data.search(query, filters)
            )
        }
    }

    fun updateFilterState(newFilterState: FilterState) {
        _uiState.update { currentState ->
            currentState.copy(
                filterState = newFilterState
            )
        }
    }
}