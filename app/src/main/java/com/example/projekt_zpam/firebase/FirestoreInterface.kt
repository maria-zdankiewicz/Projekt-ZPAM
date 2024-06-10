package com.example.projekt_zpam.firebase

import com.example.projekt_zpam.MeasurementData

interface FirestoreInterface {

    // Dodawanie danych do Firestore
    suspend fun addUser(data: FireUserData, UserId: String)

    //Aktualizacja danych w Firestore na podstawei ID dokumentu
    suspend fun updateUser( UserId: String, updatedUser: FireUserData)

    //Pobieranie danych z Firestore
    suspend fun getUser(UserId: String): FireUserData?

    //Usuwanie danych z Firestore na podstawie id dokumentu
    suspend fun deleteUser(documentId: String)

    suspend fun saveData(UserId: String, badanie: MeasurementData)

}