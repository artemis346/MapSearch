package com.mapsearch.repositories_impl.mappers

import com.mapsearch.network.api.nearby.NearbyResponse
import com.mapsearch.repositories.dto.HubsDto

fun NearbyResponse.mapToDomain(): List<HubsDto> {
    return hubs?.let { list ->
        val result = mutableListOf<HubsDto>()
        list.forEach {
            if (it.latitude.isNotEmpty() || it.longitude.isNotEmpty())
                result.add(
                    HubsDto(
                        id = it.id,
                        lat = it.latitude.toDouble(),
                        lng = it.longitude.toDouble(),
                        name = it.name
                    )
                )
        }
        return result
    } ?: listOf()
}