package com.mapsearch.repositories_impl.di

import com.mapsearch.repositories.ICacheDataSource
import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.ISearchRepository
import com.mapsearch.repositories.ISelectedRepository
import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.repositories_impl.HubsCacheDataSource
import com.mapsearch.repositories_impl.NearbyRepository
import com.mapsearch.repositories_impl.SearchRepository
import com.mapsearch.repositories_impl.SelectedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepositoriesModule {

    @Singleton
    @Binds
    abstract fun provideNearbyRepository(repImpl: NearbyRepository) : INearbyRepository

    @Singleton
    @Binds
    abstract fun provideSearchHubRepository(repImpl: SearchRepository) : ISearchRepository

    @Singleton
    @Binds
    abstract fun provideSelectedHubRepository(repImpl: SelectedRepository) : ISelectedRepository

    @Singleton
    @Binds
    abstract fun provideHubsCache(repImpl: HubsCacheDataSource) : ICacheDataSource<HubsDto, String>
}