package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.projekt_zpam.firebase.FirestoreHandler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class InputDataActivity :  BaseActivity() {

    // Referencja do obiektu FirebaseFirestore do interakcji z bazą danych Firestore
    val db = Firebase.firestore

    private lateinit var auth: FirebaseAuth

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioFasting: RadioButton
    private lateinit var radioAfterMeal: RadioButton

    private var selectedMeasurementType: MeasurementType? = null

    // Obiekt do obsługi operacji na bazie danych Firestore
    private val dbOperations = FirestoreHandler(db)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inputdata)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val UserId = currentUser!!.uid

        // Inicjalizacja wszystkich widżetów interfejsu użytkownika
        val SaveButton = findViewById<Button>(R.id.SaveButton)
        var InputLevel = findViewById<EditText>(R.id.inputGlucose)
        radioGroup = findViewById(R.id.radioGroup)
        radioFasting = findViewById(R.id.radioFasting)
        radioAfterMeal = findViewById(R.id.radioAfterMeal)
        val backButton = findViewById<Button>(R.id.backButton)

        // Ustawienie nasłuchiwaczy kliknięć dla przycisków
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedMeasurementType = when (checkedId) {
                R.id.radioFasting -> MeasurementType.FASTING
                R.id.radioAfterMeal -> MeasurementType.AFTER_MEAL
                else -> null
            }
        }

        SaveButton.setOnClickListener {
            try{
            val numberText = InputLevel.text.toString()
            if (selectedMeasurementType != null && numberText.isNotEmpty()) {
                val glucose_level = numberText.toInt()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val currentDate = dateFormat.format(Date())
                if (glucose_level != null) {
                    // Utworzenie obiektu pomiaru
                    val badanie = MeasurementData(selectedMeasurementType, glucose_level, currentDate)
                    // Uruchomienie korutyny w wątku głównym
                    GlobalScope.launch(Dispatchers.Main) {
                        // Dodanie pomiaru do bazy danych Firestore
                        dbOperations.saveData(UserId, badanie)
                    }
                    // Wyślij powiadomienie po pobraniu danych
                    sendNotification(this, "New Measurements", "New glucose measurements have been added.")
                    // po 2s przechodzi do menu
                    val splashScreenDuration: Long = 2000
                    Handler().postDelayed({
                        val intent = Intent(this@InputDataActivity, MenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, splashScreenDuration)
                } else {
                    showErrorSnackBar(resources.getString(R.string.err_msg_add_to_database), true)
                }

            }
        }catch (e: Exception) {
                // Obsługa błędów
            }
        }

        backButton?.setOnClickListener{
            gotoMenuActivity()
        }
    }

    private fun gotoMenuActivity(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }


}