package com.mapsearch.search.mapper

import com.google.android.gms.maps.model.LatLng
import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.search.dto.HubItem

fun HubsDto.mapToItem(): HubItem {
    return HubItem(
        id = id,
        name = name
    )
}