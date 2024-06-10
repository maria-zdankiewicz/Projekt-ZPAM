package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Główna aktywność aplikacji, wyświetlająca powitanie użytkownika.
 */
open class WelcomeActivity : AppCompatActivity() {


    private var welcomeTextView: TextView? = null
    private val splashScreenDuration: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Pobranie identyfikatora użytkownika przekazanego z poprzedniej aktywności
        val uID = intent
        val userID = uID.getStringExtra("uID")

        // Inicjalizacja pola tekstowego powitania i ustawienie tekstu powitalnego
        welcomeTextView = findViewById(R.id.welcomeText)
        welcomeTextView?.text = "Welcome ${userID}!";

        Handler().postDelayed({
            // Start MenuActivity after 5 seconds
            val intent = Intent(this@WelcomeActivity, MenuActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }, splashScreenDuration)
    }
}