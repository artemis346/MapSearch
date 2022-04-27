package com.mapsearch.network.api.hubs

import com.google.gson.annotations.SerializedName

data class NearbyHubs(
    @SerializedName("id")
    val id: Long,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("name")
    val name: String,
)