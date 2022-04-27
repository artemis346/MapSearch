package com.mapsearch.repositories_impl

import com.mapsearch.network.api.hubs.HubsSearchApi
import com.mapsearch.repositories.ICacheDataSource
import com.mapsearch.repositories.ISearchRepository
import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.repositories_impl.mappers.mapToDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: HubsSearchApi,
    private val cache: ICacheDataSource<HubsDto, String>
) : ISearchRepository {

    override fun searchByQuery(query: String): Flow<List<HubsDto>> {
        return flow {
            val response = api.getNearBy(query = query)
            val list = response.mapToDomain()
            cache.saveDataToCache(list)
            emit(list)
        }.flowOn(Dispatchers.IO)
    }
}