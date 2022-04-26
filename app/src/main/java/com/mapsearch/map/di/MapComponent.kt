package com.mapsearch.map.di

import com.mapsearch.map.MapFragment
import dagger.Component

@Component(dependencies = [MapModuleDependencies::class])
interface MapComponent {

    fun inject(fmt: MapFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(deps: MapModuleDependencies): Builder
        fun build(): MapComponent
    }
}