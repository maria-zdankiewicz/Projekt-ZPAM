package com.example.projekt_zpam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class WelcomeActivity : AppCompatActivity() {

    private var welcomeTextView: TextView? = null
    private val splashScreenDuration: Long = 2000 // 2 sekundy

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
            // Przechodzi do Menu po 2 sekundach
            val intent = Intent(this@WelcomeActivity, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }
}