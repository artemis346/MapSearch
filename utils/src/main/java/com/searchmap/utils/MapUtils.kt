package com.searchmap.utils

import com.google.android.gms.maps.model.LatLng

val DEFAULT_ZOOM = 16f
val DEFAUL_COORDINATE = LatLng(55.6757884,12.6018851)
val MAX_VISIBLE_RADIUS = 1000

fun isRadiusLessThanMax(radius: Double)  =
    radius < MAX_VISIBLE_RADIUS