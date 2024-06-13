package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RecycleActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private val measurements = mutableListOf<MeasurementData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle)

        // Inicjalizacja elementów interfejsu
        recyclerView = findViewById(R.id.recyclerView)
        val backButton = findViewById<Button>(R.id.backButton)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(measurements)
        recyclerView.adapter = adapter

        // Obiekty do obsługi bazy danych i autentyfikacji
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {

            val userId = currentUser.uid

            // Pobranie pomiarów i dodanie do wyświetlanej listy
            db.collection("badania")
                .document(userId)
                .collection("pomiary")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val measurement = document.toObject(MeasurementData::class.java)
                            .copy(formattedDate = document.id)
                        measurements.add(measurement)
                    }
                    adapter.notifyDataSetChanged()

                }
                .addOnFailureListener { exception ->
                    Log.w("RecycleActivity", "Error getting documents: ", exception)
                }
        } else {
            Log.e("RecycleActivity", "No user is logged in")
        }

        backButton?.setOnClickListener {
            gotoMenuActivity()
        }
    }

    private fun gotoMenuActivity() {
        /**
         * Funkcja przychodząca do menu
         */
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
    
}
