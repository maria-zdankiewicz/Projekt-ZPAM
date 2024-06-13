package com.example.projekt_zpam

import com.google.firebase.firestore.PropertyName

data class MeasurementData(
    /**
     * Klasa przechowująca dane o pomiarze
     */
    @get: PropertyName("type") @set:PropertyName("type") var type: MeasurementType? = null,
    @get:PropertyName("level") @set:PropertyName("level") var level: Int = 0,
    var formattedDate: String
) {
    // Firebase Firestore wymaga, aby klasy danych używane do deserializacji miały
    // domyślny konstruktor bezargumentowy (Był problem przy RecycleView
    constructor() : this(type = null, 0,"")
}
