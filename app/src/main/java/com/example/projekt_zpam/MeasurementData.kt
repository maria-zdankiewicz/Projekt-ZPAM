package com.example.projekt_zpam

import com.google.firebase.firestore.PropertyName

data class MeasurementData(
    @get: PropertyName("type") @set:PropertyName("type") var type: MeasurementType? = null,
    @get:PropertyName("level") @set:PropertyName("level") var level: Int = 0,
    var formattedDate: String
    ) {
    constructor() : this(type = null, 0,"")
}



