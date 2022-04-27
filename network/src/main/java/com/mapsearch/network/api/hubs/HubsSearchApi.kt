package com.mapsearch.network.api.hubs

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HubsSearchApi {

    @GET("api/owners/admins/{adminId}/hubs/search")
    suspend fun getNearBy(
        @Path("adminId")
        adminId: Int = 1090,

        @Query("query")
        query: String,
    ): List<NearbyHubs>
}