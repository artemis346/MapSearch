package com.mapsearch.map.dto

import com.google.android.gms.maps.model.LatLng

data class MarkerItem(
    var coordinate: LatLng,
    var name: String
)