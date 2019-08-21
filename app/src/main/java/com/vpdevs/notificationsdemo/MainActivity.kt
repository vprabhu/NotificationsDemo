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

    private val mCHANNELID = "notifications_demo"
    private val mNOTIFICATIONID = 0
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        button_notification_create.setOnClickListener {
            createNotifications()
        }

        button_notification_cancel.setOnClickListener {
            notificationManager.cancel(mNOTIFICATIONID)
        }

        button_notification_update.setOnClickListener {
            updateNotification()
        }
    }

    private fun updateNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        notificationManager.notify(
            mNOTIFICATIONID,
            getNotificationBuilder(
                "Updated Notification",
                "This is a Simple,Basic and Updated Notification"
            )?.build()
        )
    }

    private fun createNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        notificationManager.notify(
            mNOTIFICATIONID,
            getNotificationBuilder(
                "Basic Notification",
                "This is a Simple and Basic Notification"
            )?.build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(): NotificationChannel {
        val name = "Vhp"
        val descriptionText = "This is a simple and Basic Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(mCHANNELID, name, importance).apply {
            description = descriptionText
            enableVibration(true)
        }
    }

    private fun getNotificationBuilder(title: String, description: String): NotificationCompat.Builder? {
        return NotificationCompat.Builder(this, mCHANNELID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(description)
    }
}
