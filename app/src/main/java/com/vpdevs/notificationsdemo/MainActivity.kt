package com.vpdevs.notificationsdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "notificationsdemo"
    private val NOTIFICATION_ID = 0
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        button_notification_create.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationAboveAPI26()
            } else {
                createNotificationBelowAPI26()
            }
        }

        button_notification_cancel.setOnClickListener {
            notificationManager.cancel(NOTIFICATION_ID)
        }

        button_notification_update.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                updateNotification()
            }
        }
    }

    private fun createNotificationBelowAPI26() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Basic Notification")
            .setContentText("This is a Simple and Basic Notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        builder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationAboveAPI26() {
        val name = "Vhp"
        val descriptionText = "This is a simple and Basic Notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            enableVibration(true)
        }
        val notifyBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Basic Notification")
            .setContentText("This is a Simple and Basic Notification")
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateNotification() {
        val name = "Vhp"
        val descriptionText = "This is a simple and Basic Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            enableVibration(true)
        }
        val notifyBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Updated Notification")
            .setContentText("This is a Simple , Basic and Updated Notification")
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build())
    }
}
