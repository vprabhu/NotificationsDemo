package com.vpdevs.notificationsdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationDismissReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context , "Notification Dismissed" , Toast.LENGTH_SHORT).show()
    }
}
