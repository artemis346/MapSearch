package com.mapsearch.nearby.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.searchmap.utils.DEFAULT_ZOOM
import com.searchmap.utils.DEFAUL_COORDINATE

sealed class SelectedItemState {

    data class Success(val marker: com.mapsearch.nearby.dto.MarkerItem) : SelectedItemState()

    data class Initial(
        val centerLocation: LatLng = DEFAUL_COORDINATE,
        val zoom: Float = DEFAULT_ZOOM
    ) : SelectedItemState()

    object Loading : SelectedItemState()
}