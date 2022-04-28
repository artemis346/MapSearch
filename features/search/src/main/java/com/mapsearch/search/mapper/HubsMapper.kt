package com.mapsearch.search.mapper

import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.search.dto.HubItem

fun HubsDto.mapToItem(): HubItem {
    return HubItem(
        id = id,
        name = name
    )
}