package com.example.projekt_zpam.firebase

import com.example.projekt_zpam.MeasurementData

interface FirestoreInterface {

    // Dodawanie pomiaru na podstawie id użytkownika
    suspend fun saveData(UserId: String, badanie: MeasurementData)

    // Usuwanie pomiaru na podstawie
    suspend fun deleteData(UserId: String, badanie: MeasurementData)
}