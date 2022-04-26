package com.mapsearch.map.viewmodel

import com.mapsearch.map.dto.MarkerItem
import kotlinx.coroutines.flow.Flow

sealed class MapUIState {
    data class Success(val markers: List<MarkerItem>) : MapUIState()
    data class Error(val error: ErrorState) : MapUIState()
    object Initial : MapUIState()
    object Loading : MapUIState()
}

enum class ErrorState(/*@StringRes val message: Int*/) {
    ERROR_LOADING(/*R.string.error_content_try_again*/)
}