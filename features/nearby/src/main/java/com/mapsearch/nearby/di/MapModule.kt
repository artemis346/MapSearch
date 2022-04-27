package com.mapsearch.nearby.di

import com.mapsearch.nearby.viewmodel.MapListViewModel
import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.ISelectedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal class MapModule {

    @Provides
    fun provideViewModel(repository: INearbyRepository, selectedRepository: ISelectedRepository) : MapListViewModel {
        return MapListViewModel(repository, selectedRepository)
    }
}