package com.mapsearch.repositories

import com.mapsearch.repositories.dto.HubsDto
import kotlinx.coroutines.flow.Flow

interface INearbyRepository {

    fun findNearby(lat: Double, lng: Double, radius: Int): Flow<List<HubsDto>>
}