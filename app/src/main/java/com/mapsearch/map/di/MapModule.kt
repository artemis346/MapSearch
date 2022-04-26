package com.mapsearch.map.di

import com.mapsearch.map.viewmodel.MapListViewModel
import com.mapsearch.repositories.INearbyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
internal class MapModule {

    @Provides
    fun provideViewModel(repository: INearbyRepository) : MapListViewModel {
        return MapListViewModel(repository)
    }
}