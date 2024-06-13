package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Inicjalizacja przycisków
        val inputButton = findViewById<Button>(R.id.inputButton)
        val recycleButton = findViewById<Button>(R.id.recycleButton)


        // Ustawienie nasłuchiwania kliknięć przycisku
        inputButton?.setOnClickListener{
        gotoInputActivity()
        }

        // Ustawienie nasłuchiwania kliknięć przycisku
        recycleButton?.setOnClickListener{
            gotoRecycleActivity()
        }
    }

    private fun gotoInputActivity(){
        /**
         * Metoda przechodzenia do aktywności do wprowadzania pomiarów
         */
        val intent = Intent(this, InputDataActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun gotoRecycleActivity(){
        /**
         * Metoda przechodzenia do aktywności wyświetlającej historię pomiarów
         */
        val intent = Intent(this, RecycleActivity::class.java)
        startActivity(intent)
        finish()
    }
}