package com.mapsearch.nearby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.*
import com.mapsearch.nearby.viewmodel.ErrorMap
import com.mapsearch.nearby.viewmodel.MapListViewModel
import com.mapsearch.nearby.viewmodel.MapUiState
import com.mapsearch.nearby.viewmodel.SelectedItemState
import com.searchmap.utils.DEFAULT_ZOOM
import navigateSearch


@Composable
fun MainMapScreen(navController: NavHostController) {
    val vm: MapListViewModel = hiltViewModel()
    Box(Modifier.fillMaxSize()) {
        MapScreen(vm)
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                navController.navigateSearch()
            }) {
            Text(text = "Search")
        }
        ErrorToast(
            Modifier
                .align(Alignment.TopCenter)
                .background(colorResource(id = R.color.purple_500))
                .fillMaxWidth()
                .padding(24.dp),
            vm
        )
    }
}

@Composable
private fun MapScreen(vm: MapListViewModel) {
    LaunchedEffect(Unit, block = {
        vm.fetchCurrentPosition()
    })

    val locationState = vm.selectedItemState.collectAsState()
    val mapState = vm.uiMapState.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        val state = locationState.value
        if (state is SelectedItemState.Initial) {
            position = CameraPosition.fromLatLngZoom(state.centerLocation, state.zoom)
        }
    }

    val refreshMarkers = {
        val centerLocation = cameraPositionState.position.target
        val topLeftLocation = cameraPositionState.projection?.visibleRegion?.farLeft
            ?: cameraPositionState.position.target
        val radius = SphericalUtil.computeDistanceBetween(topLeftLocation, centerLocation)
        vm.fetchMarkers(centerLocation, radius = radius)
    }

    if (!cameraPositionState.isMoving) {
        refreshMarkers()
    }

    MapView(
        cameraPositionState = cameraPositionState,
        refreshMarkers = refreshMarkers,
        locationState = locationState,
        mapState = mapState
    )
}

@Composable
fun MapView(
    cameraPositionState: CameraPositionState,
    refreshMarkers: () -> Unit,
    locationState: State<SelectedItemState>,
    mapState: State<MapUiState>
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        ),
        onMapLoaded = refreshMarkers
    ) {
        if (locationState.value is SelectedItemState.Success) {
            val selectedState = locationState.value as SelectedItemState.Success
            Marker(
                state = rememberMarkerState(position = selectedState.marker.coordinate),
                title = selectedState.marker.name
            )
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(
                    selectedState.marker.coordinate,
                    DEFAULT_ZOOM
                )
            )
        } else {
            val mapStateVal = mapState.value
            if (mapStateVal is MapUiState.Success) {
                mapStateVal.markers.forEach {
                    Marker(rememberMarkerState(position = it.coordinate), title = it.name)
                }
            }
        }
    }
}


@Composable
fun ErrorToast(modifier: Modifier, vm: MapListViewModel) {
    val state = vm.uiMapState.collectAsState().value
    if (state is MapUiState.Error) {
        if (state.error == ErrorMap.LargeZoomLevel) {
            Text(
                modifier = modifier,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.white),
                text = stringResource(id = state.error.message!!)
            )
        }
    }
}
