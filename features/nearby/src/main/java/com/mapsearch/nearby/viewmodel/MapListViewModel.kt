package com.mapsearch.nearby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mapsearch.nearby.mappers.mapToMarker
import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.ISelectedRepository
import com.searchmap.utils.MAX_VISIBLE_RADIUS
import com.searchmap.utils.isRadiusLessThanMax
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapListViewModel @Inject constructor(
    private var repository: INearbyRepository,
    private val selectedRepository: ISelectedRepository
) : ViewModel() {

    var prevCoord: LatLng? = null
    var lastRadius: Double? = null

    private val selectedItem =
        MutableStateFlow<SelectedItemState>(SelectedItemState.Initial())
    val locationState: StateFlow<SelectedItemState> = selectedItem

    private val mapState =
        MutableStateFlow<MapUIState>(MapUIState.Initial)
    val uiState: StateFlow<MapUIState> = mapState

    fun fetchMarkers(coord: LatLng, radius: Double) {
        selectedItem.value = SelectedItemState.Loading
        viewModelScope.launch {
            flow {
                if (prevCoord != coord || radius != lastRadius) {
                    prevCoord = coord
                    lastRadius = radius
                    emit(radius)
                }
            }
                .dropWhile {
                    val result = !isRadiusLessThanMax(it)
                    if(result) {
                        mapState.value = MapUIState.Error(ErrorMap.LargeZoomLevel)
                    }

                    result
                }
                .debounce(500)
                .flatMapConcat {
                    repository.findNearby(
                        lat = coord.latitude,
                        lng = coord.longitude,
                        radius = radius.toInt()
                    )
                }
                .catch { exception ->
                    mapState.value = MapUIState.Error(ErrorMap.LoadingError)
                }
                .map { list ->
                    list.map {
                        it.mapToMarker()
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    mapState.value = MapUIState.Success(it)
                }
        }
    }

    fun fetchCurrentPosition() {
        viewModelScope.launch {
            selectedRepository.getSelectedItem()
                .catch {
                    selectedItem.value = SelectedItemState.Initial()
                }
                .collect {
                    if (it != null) {
                        selectedItem.value = SelectedItemState.Success(it.mapToMarker())
                    } else {
                        selectedItem.value = SelectedItemState.Initial()
                    }
                }
        }
    }
}