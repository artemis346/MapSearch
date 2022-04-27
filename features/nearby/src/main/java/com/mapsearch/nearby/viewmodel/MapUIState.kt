package com.mapsearch.nearby.viewmodel

import androidx.annotation.StringRes
import com.mapsearch.nearby.R

sealed class MapUIState {
    data class Success(val markers: List<com.mapsearch.nearby.dto.MarkerItem>) : MapUIState()
    data class Error(val error: ErrorMap) : MapUIState()
    object Initial : MapUIState()
    object Loading : MapUIState()
}

enum class ErrorMap(@StringRes val message: Int? = null) {
    LargeZoomLevel(R.string.error_zoom_level),
    LoadingError()
}