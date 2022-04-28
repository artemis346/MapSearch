package com.mapsearch.nearby.di

import com.mapsearch.nearby.viewmodel.NearbyViewModel
import com.mapsearch.repositories.INearbyRepository
import com.mapsearch.repositories.ISelectedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal class NearbyModule {

    @Provides
    fun provideViewModel(repository: INearbyRepository, selectedRepository: ISelectedRepository) : NearbyViewModel {
        return NearbyViewModel(repository, selectedRepository)
    }
}