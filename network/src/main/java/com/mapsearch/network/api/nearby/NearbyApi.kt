package com.mapsearch.network.api.nearby

import retrofit2.http.GET
import retrofit2.http.Query

interface NearbyApi {

    @GET("api/public/nearby")
    suspend fun getNearBy(
        @Query("filter_type")
        filterType: String = "radius",

        @Query("location")
        location: String,

        @Query("radius")
        radius: Int,
    ): NearbyResponse
}