package com.mapsearch.repositories

import com.mapsearch.repositories.dto.HubsDto
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {

    fun searchByQuery(query: String): Flow<List<HubsDto>>
}