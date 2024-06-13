package com.example.projekt_zpam.firebase

import com.example.projekt_zpam.MeasurementData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FirestoreHandler(private val db: FirebaseFirestore) : FirestoreInterface {


    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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

    override suspend fun deleteData(UserId: String, badanie: MeasurementData) {
        /**
         * Usuwa pomiar z Firebase
         *  @param UserId unikalny identyfikator zalogowanego użytkownika
         * @param badanie obiekt klasy przechowywującej dane pomiaru.
         * */
        try {
            val documentId2 = badanie.formattedDate
            db.collection("badania")
                .document(UserId)
                .collection("pomiary")
                .document(documentId2)
                .delete()
                .await()


        }catch (e: Exception) {
            // Obsługa błędów
        }
    }
}

