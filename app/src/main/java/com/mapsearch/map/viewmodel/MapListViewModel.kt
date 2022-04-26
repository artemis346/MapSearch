package com.mapsearch.map.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mapsearch.R
import com.mapsearch.map.dto.MarkerItem
import com.mapsearch.repositories.INearbyRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

class MapListViewModel @Inject constructor(private var repository: INearbyRepository) :
    ViewModel() {

    private val listData =
        MutableStateFlow<MapUIState>(MapUIState.Initial)
    val uiState: StateFlow<MapUIState> = listData

    fun fetchMarkers(coord: LatLng, radius: Double) {
        viewModelScope.launch {
            repository.findNearby(
                lat = coord.latitude,
                lng = coord.longitude,
                radius = radius.toInt()
            )
                .debounce(2000)
                .catch { exception ->
                    exception.printStackTrace()
                }
                .map { list ->
                    list.map {
                        MarkerItem(
                            coordinate = LatLng(it.lat, it.lng),
                            name = it.name
                        )
                    }
                }
                .collect {
                    listData.value = MapUIState.Success(it)
                }
        }
    }
}