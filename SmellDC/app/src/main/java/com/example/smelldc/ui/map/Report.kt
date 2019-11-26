package com.example.smelldc.ui.map

import com.google.firebase.firestore.GeoPoint

data class Report(
    var activity: String? = "",
    var location: GeoPoint = GeoPoint(0.0,0.0),
    var odor: String? = "",
    var quality: Int? = 0,
    var symptoms: String? = "",
    var toBeReported: Boolean? = false
)
