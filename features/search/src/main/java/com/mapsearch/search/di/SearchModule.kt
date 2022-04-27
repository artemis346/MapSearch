package com.mapsearch.search.di

import com.mapsearch.repositories.ISearchRepository
import com.mapsearch.repositories.ISelectedRepository
import com.mapsearch.search.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal class SearchModule {

    @Provides
    fun provideViewModel(repository: ISearchRepository, selectedRepository: ISelectedRepository) : SearchViewModel {
        return SearchViewModel(repository, selectedRepository)
    }
}