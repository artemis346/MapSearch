package com.mapsearch.repositories_impl

import com.mapsearch.network.api.nearby.NearbyApi
import com.mapsearch.repositories.ICacheDataSource
import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.repositories_impl.mappers.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NearbyRepository @Inject constructor(
    private val api: NearbyApi,
    private val cache: ICacheDataSource<HubsDto, String>
) : INearbyRepository {

    override fun findNearby(lat: Double, lng: Double, radius: Int): Flow<List<HubsDto>> {
        return flow {
            val response = api.getNearBy(
                location = "$lat,$lng",
                radius = radius
            )
            val list = response.mapToDomain()
            cache.saveDataToCache(list)
            emit(list)
        }
    }
}