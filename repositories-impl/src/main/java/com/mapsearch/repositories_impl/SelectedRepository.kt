package com.mapsearch.repositories_impl

import com.mapsearch.repositories.ICacheDataSource
import com.mapsearch.repositories.ISelectedRepository
import com.mapsearch.repositories.dto.HubsDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SelectedRepository @Inject constructor(
    private val cache: ICacheDataSource<HubsDto, String>
) : ISelectedRepository {

    private var selectedHub: HubsDto? = null

    override suspend fun selectItem(key: String) {
        selectedHub = cache.findItem(key)
    }

    override suspend fun getSelectedItem(): Flow<HubsDto?> = flowOf(selectedHub)
}