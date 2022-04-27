package com.mapsearch.nearby.mappers

import com.google.android.gms.maps.model.LatLng
import com.mapsearch.nearby.dto.MarkerItem
import com.mapsearch.repositories.dto.HubsDto

fun HubsDto.mapToMarker(): MarkerItem {
    return MarkerItem(
        coordinate = LatLng(lat, lng),
        name = name
    )
}