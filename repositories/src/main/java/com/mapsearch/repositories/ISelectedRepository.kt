package com.mapsearch.repositories

import com.mapsearch.repositories.dto.HubsDto
import kotlinx.coroutines.flow.Flow

interface ISelectedRepository {
    suspend fun selectItem(key: String)
    suspend fun getSelectedItem(): Flow<HubsDto?>
}