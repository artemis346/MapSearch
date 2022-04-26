package com.mapsearch.repositories_impl.di

import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.ISearchRepository
import com.mapsearch.repositories_impl.NearbyRepository
import com.mapsearch.repositories_impl.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideNearbyRepository(repImpl: NearbyRepository) : INearbyRepository

    @Binds
    abstract fun provideSearchHubRepository(repImpl: SearchRepository) : ISearchRepository
}