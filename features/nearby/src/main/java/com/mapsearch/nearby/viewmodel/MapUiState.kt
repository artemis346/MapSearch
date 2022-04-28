package com.mapsearch.nearby.viewmodel

import androidx.annotation.StringRes
import com.mapsearch.nearby.R

sealed class MapUiState {
    data class Success(val markers: List<com.mapsearch.nearby.dto.MarkerItem>) : MapUiState()
    data class Error(val error: ErrorMap) : MapUiState()
    object Initial : MapUiState()
}

enum class ErrorMap(@StringRes val message: Int? = null) {
    LargeZoomLevel(R.string.error_zoom_level),
    LoadingError
}