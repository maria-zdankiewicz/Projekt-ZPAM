package com.example.projekt_zpam.firebase

import com.example.projekt_zpam.MeasurementData
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FirestoreHandler(private val db: FirebaseFirestore) : FirestoreInterface {


    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun addUser(data: FireUserData, UserId: String) {
        try {
            db.collection("students").document(UserId).set(data).await()
        } catch (e: Exception) {
            // Obsługa błędów, np. logowanie, zwracanie wartości lub propagacja wyjątku
        }
    }

        override suspend fun deleteUser(UserId: String) {
            try {
                db.collection("students").document(UserId).delete().await()
            } catch (e: Exception) {
                // Obsługa błędów
            }
        }

        override suspend fun updateUser( UserId: String, updatedUser: FireUserData) {
            try {
                db.collection("students").document(UserId).set(updatedUser).await()
            } catch (e: Exception) {
                // Obsługa błędów
            }
        }

        override suspend fun getUser(UserId: String): FireUserData? {
            val snapshot = FirebaseFirestore.getInstance().collection("")
                .whereEqualTo(FieldPath.documentId(), UserId)
                .get()
                .await()

            return snapshot.documents.firstOrNull()?.toObject(FireUserData::class.java)
        }


        override suspend fun saveData(UserId: String, badanie: MeasurementData){
            /**
             * Zapisuje pomiar w odpowiednim miejscu w Firebase
             * @param UserId unikalny identyfikator zalogowanego użytkownika
             * @param badanie obiekt klasy przechowywującej dane pomiaru.
             */
        try {
            // Pobieranie aktualnej daty jako string
            val dateFormat = SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.getDefault())
            val currentDate = dateFormat.format(Date())
            db.collection("badania").document(UserId).collection("pomiary")
                .document(currentDate.toString()).set(badanie).await()
        }catch (e: Exception) {
            // Obsługa błędów
        }
    }
}
