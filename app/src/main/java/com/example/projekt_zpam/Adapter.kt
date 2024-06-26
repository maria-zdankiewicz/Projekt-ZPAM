package com.example.projekt_zpam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt_zpam.firebase.FirestoreHandler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Adapter(private val measurements: MutableList<MeasurementData>) :
     RecyclerView.Adapter<Adapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Elementy każdego elementu listy w RecycleView
        val textViewValue: TextView = itemView.findViewById(R.id.textViewlevel)
        val textViewMode: TextView = itemView.findViewById(R.id.textViewMode)
        val textViewTimestamp: TextView = itemView.findViewById(R.id.textViewTimestamp)
        val buttonDelete: Button = itemView.findViewById(R.id.deleteButton)
}

    // Referencja do obiektu FirebaseFirestore do interakcji z bazą danych Firestore
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    // Obiekt do obsługi operacji na bazie danych Firestore
    private val dbOperations = FirestoreHandler(db)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /**
         * Tworzy nowy widok na podstawie layoutu XML dla danego typu elementu w RecyclerView.
         * Tworzy i zwraca nową instancję ViewHoldera która przechowuje ten widok
         * @param parent Grupa, do której zostanie dołączony nowo utworzony widok po utworzeniu.
         * @param viewType Typ widoku, jesli nasz recyclerView posiada wiele typów.
         * @return ViewHolder zawierający nowo utworzony widok.
         */
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_item, parent, false)
        return ViewHolder(itemView)
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         *  Wiąże dane z określonymi elementami w RecyclerView
         *  @param holder ViewHolder, którego dane mają być zaktualizowane.
         *  @param position Pozycja elementu w zestawie danych.
         */
        val measurement = measurements[position]
        holder.textViewValue.text = "Glucose level: ${measurement.level} mg/dl"
        holder.textViewMode.text = "Mode: ${measurement.type}"
        holder.textViewTimestamp.text = "Date and time: ${measurement.formattedDate}"
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val UserId = currentUser!!.uid
        holder.buttonDelete.setOnClickListener {
            // Uruchomienie korutyny w wątku głównym
            GlobalScope.launch(Dispatchers.Main) {
                // Usunięcie pomiaru z bazy danych Firestore i listy pomiarów w RecycleView
                dbOperations.deleteData(UserId, measurement)
                measurements.removeAt(position)
                notifyItemRemoved(position)

            }

        }
    }


    override fun getItemCount(): Int {
        /**
         * Zwraca liczbę elementów w zestawie danych.
         * @return Liczba elementów w zestawie danych.
         */
        return measurements.size
    }



}

