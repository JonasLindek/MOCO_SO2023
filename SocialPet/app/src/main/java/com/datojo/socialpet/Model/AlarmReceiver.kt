package com.datojo.socialpet.Model

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.datojo.socialpet.MainActivity
import com.datojo.socialpet.R

class AlarmReceiver: BroadcastReceiver() {
    private var notificationManager: NotificationManagerCompat? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmItem = intent?.getSerializableExtra("alarm_item") as? AlarmItem
        val tapResultIntent = Intent(context, MainActivity::class.java)
        tapResultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, tapResultIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = context?.let {
            NotificationCompat.Builder(it, "STAT_CHANNEL")
                .setSmallIcon(R.drawable.heart)
                .setContentTitle("Status Reminder")
                .setContentText(alarmItem?.message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        notificationManager = context?.let { NotificationManagerCompat.from(it) }
        notification?.let { alarmItem?.let { it1 ->  notificationManager?.notify(it1.id, it)} }
    }
}