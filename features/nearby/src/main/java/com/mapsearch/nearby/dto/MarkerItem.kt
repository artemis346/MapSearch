package com.mapsearch.nearby.dto

import com.google.android.gms.maps.model.LatLng

data class MarkerItem(
    var coordinate: LatLng,
    var name: String
)