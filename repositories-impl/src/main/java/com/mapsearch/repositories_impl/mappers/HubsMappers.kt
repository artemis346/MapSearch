package com.mapsearch.repositories_impl.mappers

import com.mapsearch.network.api.hubs.NearbyHubs
import com.mapsearch.repositories.dto.HubsDto

fun List<NearbyHubs>.mapToDomain(): List<HubsDto> {
    val result = mutableListOf<HubsDto>()
    forEach {
        if (it.latitude.isNotEmpty() || it.longitude.isNotEmpty()) {
            result.add(it.mapToDomain())
        }
    }
    return result
}

fun NearbyHubs.mapToDomain() =
    HubsDto(
        id = id.toString(),
        lat = latitude.toDouble(),
        lng = longitude.toDouble(),
        name = name
    )

