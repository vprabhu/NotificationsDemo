package com.vpdevs.notificationsdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput


class ReplyReceiver : BroadcastReceiver() {

    private val mReplyNotificationID = 1
    private val mChannelID = "notifications_demo"

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context , getReplyMessage(intent) , Toast.LENGTH_LONG).show()
        updateReplyNotifications(context)
    }

    private fun getReplyMessage(intent: Intent): String {
        val resultsFromIntent = RemoteInput.getResultsFromIntent(intent)
        val charSequence = resultsFromIntent.getCharSequence(Constants.KEY_TEXT_REPLY)
        return charSequence.toString()
    }

    private fun updateReplyNotifications(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel())
        }

        val builder = NotificationCompat.Builder(context, mChannelID)
            .setSmallIcon(R.drawable.ic_done)
            .setContentText("Replied")
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
        notificationManager.notify(mReplyNotificationID, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(): NotificationChannel {
        val name = "Vhp"
        val descriptionText = "This is a simple and Basic Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(mChannelID, name, importance).apply {
            description = descriptionText
            enableVibration(true)
        }
    }
}
