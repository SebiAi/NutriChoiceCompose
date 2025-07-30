package com.sebiai.nutrichoicecompose.screens.viewmodels

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import com.sebiai.nutrichoicecompose.dataclasses.AFood
import com.sebiai.nutrichoicecompose.dataclasses.Data
import com.sebiai.nutrichoicecompose.dataclasses.FilterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO: [NOW] Use Saved State module for ViewModel to keep the data when a system-initiated process death occurs: https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate

data class SharedSearchFunctionUiState(
    val searchQuery: String = "",
    val filterState: FilterState = FilterState()
)

data class HomeScreenUiState(
    val recentlyViewedFoods: List<AFood> = listOf(),

    val recentlyViewedScrollState: LazyListState = LazyListState()
)

data class SearchAndResultsScreenUiState(
    val searchResults: List<AFood> = listOf(),

    val resultScrollState: LazyListState = LazyListState()
)

class HomeAndSearchResultsScreenViewModel : ViewModel() {
    /*
     * Screen UI state
     */
    private val _sharedSearchFunctionUiState = MutableStateFlow(SharedSearchFunctionUiState())
    val sharedSearchFunctionUiState: StateFlow<SharedSearchFunctionUiState> = _sharedSearchFunctionUiState.asStateFlow()

    private val _homeScreenUiState = MutableStateFlow(HomeScreenUiState())
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()

    private val _searchAndResultsScreenUiState = MutableStateFlow(SearchAndResultsScreenUiState())
    val searchAndResultsScreenUiState: StateFlow<SearchAndResultsScreenUiState> = _searchAndResultsScreenUiState.asStateFlow()

    /*
     * Business logic
     */
    fun updateSearchQuery(newQuery: String) {
        _sharedSearchFunctionUiState.update { currentState ->
            currentState.copy(
                searchQuery = newQuery
            )
        }
    }

    fun addRecentlyViewedFood(food: AFood) {
        _homeScreenUiState.update { currentState ->
            currentState.copy(
                recentlyViewedFoods = listOf(food) + currentState.recentlyViewedFoods
            )
        }
    }

    fun performSearch(query: String, filters: FilterState) {
        _searchAndResultsScreenUiState.update { currentState ->
            currentState.copy(
                searchResults = Data.search(query, filters)
            )
        }
        _searchAndResultsScreenUiState.value.resultScrollState.requestScrollToItem(0)
    }

    fun updateFilterState(newFilterState: FilterState) {
        _sharedSearchFunctionUiState.update { currentState ->
            currentState.copy(
                filterState = newFilterState
            )
        }
    }
}