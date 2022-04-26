package com.mapsearch.map.di

import com.mapsearch.map.viewmodel.MapListViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@EntryPoint
@InstallIn(FragmentComponent::class)
interface MapModuleDependencies {
    fun mapViewModel(): MapListViewModel
}