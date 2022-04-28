package com.mapsearch.nearby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mapsearch.nearby.mappers.mapToMarker
import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.ISelectedRepository
import com.searchmap.utils.isRadiusLessThanMax
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor(
    private var repository: INearbyRepository,
    private val selectedRepository: ISelectedRepository
) : ViewModel() {

    companion object {
        const val FETCH_DEBOUNCE = 1000L
        const val FETCH_DELAY = 500L
    }

    var isFetchItem = false

    private var prevCoord: LatLng? = null
    private var lastRadius: Double? = null

    private val selectedItem =
        MutableStateFlow<SelectedItemState>(SelectedItemState.Initial())
    val selectedItemState: StateFlow<SelectedItemState> = selectedItem

    private val mapState =
        MutableStateFlow<MapUiState>(MapUiState.Initial)
    val uiMapState: StateFlow<MapUiState> = mapState

    fun fetchMarkers(coord: LatLng, radius: Double) {
        if (!isFetchItem) {
            return
        }
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
                    if (result) {
                        mapState.value = MapUiState.Error(ErrorMap.LargeZoomLevel)
                    }
                    result
                }
                .onStart {
                    delay(FETCH_DELAY)
                }
                .debounce(FETCH_DEBOUNCE)
                .flatMapConcat {
                    repository.findNearby(
                        lat = coord.latitude,
                        lng = coord.longitude,
                        radius = radius.toInt()
                    )
                }
                .catch { exception ->
                    mapState.value = MapUiState.Error(ErrorMap.LoadingError)
                }
                .map { list ->
                    list.map {
                        it.mapToMarker()
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    mapState.value = MapUiState.Success(it)
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