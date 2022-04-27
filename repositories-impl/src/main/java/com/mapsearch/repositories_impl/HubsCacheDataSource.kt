package com.mapsearch.repositories_impl

import com.mapsearch.repositories.ICacheDataSource
import com.mapsearch.repositories.dto.HubsDto
import javax.inject.Inject

class HubsCacheDataSource @Inject constructor(): ICacheDataSource<HubsDto, String> {

    private var cacheMap = hashMapOf<String, HubsDto>()

    override fun saveDataToCache(list: List<HubsDto>) {
        cacheMap.clear()
        list.map {
            cacheMap.put(it.id, value = it)
        }
    }

    override fun findItem(itemId: String) : HubsDto? {
        return cacheMap[itemId]
    }
}