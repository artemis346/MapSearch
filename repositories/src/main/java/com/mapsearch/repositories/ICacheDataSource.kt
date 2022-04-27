package com.mapsearch.repositories

interface ICacheDataSource<T, K> {
    fun saveDataToCache(list: List<T>)
    fun findItem(itemId: K) : T?
}