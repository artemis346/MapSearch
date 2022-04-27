package com.mapsearch.repositories_impl

import com.mapsearch.network.api.hubs.NearbyHubs
import com.mapsearch.network.api.nearby.Hub
import com.mapsearch.network.api.nearby.NearbyResponse

val responseHubsValid = NearbyResponse(
    hubs = listOf(
        Hub(
            id = "hub12",
            latitude = "81.00000",
            longitude = "14.00000",
            name = "Test"
        ),
        Hub(
            id = "hub13",
            latitude = "51.00000",
            longitude = "14.00000",
            name = "Test2"
        ),
        Hub(
            id = "hub15",
            latitude = "",
            longitude = "",
            name = "Test3"
        )

    )
)

val responseHubsEmpty = NearbyResponse(
    hubs = listOf(),
)

val responseHubsNull = NearbyResponse(
    hubs = null,
)

val responseSearchValid = listOf(
    NearbyHubs(
        id = 122,
        latitude = "81.00000",
        longitude = "14.00000",
        name = "Test"
    ),
    NearbyHubs(
        id = 156,
        latitude = "51.00000",
        longitude = "14.00000",
        name = "Test2"
    ),
    NearbyHubs(
        id = 1233,
        latitude = "",
        longitude = "",
        name = "Test3"
    )
)

val responseSearchEmpty = listOf<NearbyHubs>()
