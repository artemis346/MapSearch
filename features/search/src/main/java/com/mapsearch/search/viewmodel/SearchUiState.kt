package com.mapsearch.search.viewmodel

import com.mapsearch.search.dto.HubItem

sealed class SearchUiState {
    data class Success(val hubs: List<HubItem>) : SearchUiState()
    data class Error(val error: ErrorState) : SearchUiState()
    object ItemSelected : SearchUiState()
    object Initial : SearchUiState()
    object Loading : SearchUiState()
}

enum class ErrorState() {
    ERROR_LOADING()
}