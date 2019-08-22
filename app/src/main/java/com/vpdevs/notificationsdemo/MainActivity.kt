package com.vpdevs.notificationsdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mChannelID = "notifications_demo"
    private val mNotificationID = 0
    private val mReplyNotificationID = 1
    private val mProgressNotificationID = 2
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        button_notification_create.setOnClickListener {
            createNotifications()
        }

        button_notification_cancel.setOnClickListener {
            notificationManager.cancel(mNotificationID)
        }

        button_notification_update.setOnClickListener {
            updateNotification()
        }

        button_notification_reply.setOnClickListener {
            createReplyNotifications()
        }

        button_notification_show_progress.setOnClickListener {
            createProgressNotification()
        }

        button_notification_cancel_progress.setOnClickListener {
            cancelProgressNotification()
        }
    }

    private fun createNotifications() {
        val landIntent = Intent(this@MainActivity, LandingActivity::class.java)
        val landingPendingIntent: PendingIntent =
            PendingIntent.getActivity(this@MainActivity, 0, landIntent, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        notificationManager.notify(
            mNotificationID,
            getNotificationBuilder(
                "Basic Notification",
                "This is a Simple and Basic Notification"
            )?.setContentIntent(landingPendingIntent)
                ?.setDeleteIntent(getNotificationDismissIntent())
                ?.build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(): NotificationChannel {
        val name = "Download Channel"
        val descriptionText = "Controls Download Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(mChannelID, name, importance).apply {
            description = descriptionText
            enableVibration(true)
        }
    }

    private fun getNotificationBuilder(
        title: String,
        description: String
    ): NotificationCompat.Builder? {
        return NotificationCompat.Builder(this, mChannelID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(description)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
    }

    private fun updateNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        notificationManager.notify(
            mNotificationID,
            getNotificationBuilder(
                "Updated Notification",
                "This is a Simple,Basic and Updated Notification"
            )?.build()
        )
    }

    private fun createReplyNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        notificationManager.notify(
            mReplyNotificationID,
            getNotificationBuilder(
                "Basic  Reply Notification",
                "This is a Basic Reply Notification"
            )?.addAction(getReplyAction())
                ?.build()
        )
    }

    private fun getReplyAction(): NotificationCompat.Action {
        val remoteInput = RemoteInput.Builder(Constants.KEY_TEXT_REPLY).setLabel("Reply").build()

        return NotificationCompat.Action.Builder(
            R.drawable.ic_reply,
            "Reply",
            getReplyPendingIntent()
        )
            .addRemoteInput(remoteInput)
            .setAllowGeneratedReplies(true)
            .build()
    }

    private fun getReplyPendingIntent(): PendingIntent? {
        val intent: Intent
        val pendingIntent: PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = Intent(this@MainActivity, ReplyReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(
                this@MainActivity,
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            intent = Intent(this@MainActivity, LandingActivity::class.java)
            pendingIntent = PendingIntent.getActivity(
                this@MainActivity,
                100,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        return pendingIntent
    }


    private fun createProgressNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }
        val builder = getNotificationBuilder(
            "Progress Notification",
            "Download in Progress"
        )
        builder?.setProgress(100, 0, true)
        notificationManager.notify(
            mProgressNotificationID,
            builder?.build()
        )
    }

    private fun cancelProgressNotification() {
        notificationManager.cancel(mProgressNotificationID)
    }


    private fun getNotificationDismissIntent(): PendingIntent? {
        val intent = Intent(this@MainActivity, NotificationDismissReceiver::class.java)
        return PendingIntent.getBroadcast(
            this@MainActivity,
            mNotificationID,
            intent,
            0
        )
    }

}
