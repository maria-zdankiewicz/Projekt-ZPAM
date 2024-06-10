package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val inputButton = findViewById<Button>(R.id.inputButton)
        val recycleButton = findViewById<Button>(R.id.recycleButton)


        // Ustawienie nasłuchiwania kliknięć przycisku
        inputButton?.setOnClickListener{
        gotoMenuActivity()
        }

        // Ustawienie nasłuchiwania kliknięć przycisku
        recycleButton?.setOnClickListener{
            gotoRecycleActivity()
        }
    }

    private fun gotoMenuActivity(){
        val intent = Intent(this, InputDataActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun gotoRecycleActivity(){
        val intent = Intent(this, RecycleActivity::class.java)
        startActivity(intent)
        finish()
    }
}