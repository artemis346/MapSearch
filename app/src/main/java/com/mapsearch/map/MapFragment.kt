package com.mapsearch.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.*
import com.mapsearch.map.viewmodel.MapListViewModel
import com.mapsearch.map.viewmodel.MapUIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment() {

    @Inject
    lateinit var mapVM: MapListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Box(Modifier.fillMaxSize()) {
                    MapScreen(mapVM)
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(16.dp),
                        onClick = {
                            //vm.startSearch()
                        }) {
                        Text(text = "Search")
                    }
                }
            }
        }
    }

    @Composable
    private fun MapScreen(vm: MapListViewModel) {
        val amsterdam = LatLng(52.3733379, 4.9022532)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(amsterdam, 15f)
        }

        val refreshMarkers = {
//            val centerLocation = cameraPositionState.position.target
//            val topLeftLocation = cameraPositionState.projection?.visibleRegion?.farLeft ?: cameraPositionState.position.target
//            val radius = SphericalUtil.computeDistanceBetween(topLeftLocation, centerLocation)
            mapVM.fetchMarkers(cameraPositionState.position.target, radius = 10000.toDouble())
        }

        if (!cameraPositionState.isMoving) {
            refreshMarkers()
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            onMapLoaded = refreshMarkers
        ) {
            when (val state = vm.uiState.collectAsState().value) {
                is MapUIState.Success -> {
                    state.markers.forEach {
                        Log.e("coordinate:", "${it.coordinate.latitude} ${it.coordinate.longitude}" )
                        Marker(position = it.coordinate, title = it.name)
                    }
                }
                else -> {

                }
            }
//            Marker(
////                state = rememberMarkerState (position = amsterdam),
//                position = amsterdam,
//                title = "Singapore",
//                snippet = "Marker in Singapore"
//            )
        }
    }
}