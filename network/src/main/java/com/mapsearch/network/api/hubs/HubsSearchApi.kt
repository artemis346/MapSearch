package com.mapsearch.network.api.hubs

import com.mapsearch.network.api.nearby.NearbyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HubsSearchApi {

    @GET("api/owners/admin/{adminId}/hubs/search")
    suspend fun getNearBy(
        @Path("adminId")
        adminId: Int = 1090,

        @Query("query")
        query: String,
    ): NearbyResponse
}