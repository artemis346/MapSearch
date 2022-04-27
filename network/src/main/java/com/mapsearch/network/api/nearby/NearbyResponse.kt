package com.mapsearch.network.api.nearby

import com.google.gson.annotations.SerializedName

data class NearbyResponse(

    @SerializedName("hubs")
    val hubs: List<Hub>?
)

data class Hub(
    @SerializedName("id")
    val id: String,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("name")
    val name: String,
)
