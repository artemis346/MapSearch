package com.searchmap.utils

import com.google.android.gms.maps.model.LatLng

const val DEFAULT_ZOOM = 16f
const val MAX_VISIBLE_RADIUS = 1000
val DEFAUL_COORDINATE = LatLng(55.6757884, 12.6018851)

fun isRadiusLessThanMax(radius: Double) =
    radius < MAX_VISIBLE_RADIUS