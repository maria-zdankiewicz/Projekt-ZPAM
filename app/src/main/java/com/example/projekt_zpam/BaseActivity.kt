package com.example.projekt_zpam

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Klasa bazowa dla wszystkich aktywności w aplikacji.
 * Zawiera metodę do wyświetlania paska Snackbar z komunikatem.
 * Zawiera kanał powiadomień w aplikacji
 */
open class BaseActivity : AppCompatActivity() {


    fun showErrorSnackBar(message: String, errorMessage: Boolean){
        /**
         * Wyświetla pasek Snackbar z określonym komunikatem.
         * @param message Wiadomość do wyświetlenia w pasku Snackbar.
         * @param errorMessage Flaga określająca, czy komunikat jest błędem (true) lub sukcesem (false).
         */
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view

        // Ustawienie koloru paska Snackbar na podstawie typu komunikatu
        if (errorMessage) {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackbar.show()
    }

    //Stworzenie powiadomień

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Implementacji funkcji tworzącej kanał powiadomień
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        /**
         * Tworzy kanał powiadomień o nowych pomiarach glukozy
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "GlucoseMeasurementsChannel"
            val descriptionText = "Channel for glucose measurements notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun sendNotification(context: Context, title: String, message: String) {
        val intent = Intent(context, RecycleActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.glucose) // Upewnij się, że masz ikonę powiadomienia w folderze res/drawable
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, builder.build())
    }



}

