package com.datojo.socialpet.View

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.datojo.socialpet.MainActivity
import com.datojo.socialpet.Model.AlarmItem
import com.datojo.socialpet.Model.AlarmReceiver
import com.datojo.socialpet.ViewModel.PetStatus
import java.util.Date

fun predict(stats: PetStatus, context: Context) {
    val health = stats.health.value
    val hunger = stats.hunger.value
    val thirst = stats.thirst.value
    val currDate = Date()
    val interval = stats.milliSecondsInterval
    val magnitude = stats.magnitude
    val buffer = stats.buffer

    val thirstDrain =
        if (thirst > 0)
            (thirst * interval * magnitude)
        else
            0f

    var hungerDrain =
        if (hunger > 0)
            (hunger * interval * magnitude)
        else
            0f
    if (hungerDrain > thirstDrain) {
        var tmp = hunger
        tmp -= (thirstDrain / interval) / magnitude

        hungerDrain = (tmp * interval * magnitude / 2) + thirstDrain
    }

    var tmp = health + (hungerDrain / interval) / magnitude / 2
    if (tmp > 1) tmp = 1f
    val healthDrainLow =
        if (tmp > .25)
            ((tmp - .25f) * interval * magnitude) + hungerDrain
        else
            0f
    val healthDrainEmpty =
        if (tmp > 0)
            (tmp * interval * magnitude) + hungerDrain
        else
            0f

    val healthLow =
        if (healthDrainLow > 0)
            Date(currDate.time + healthDrainLow.toLong() + buffer)
        else
            null
    val healthEmpty =
        if (healthDrainEmpty > 0)
            Date(currDate.time + healthDrainEmpty.toLong() + buffer)
        else
            null
    val hungerEmpty =
        if (hungerDrain > 0)
            Date(currDate.time + hungerDrain.toLong() + buffer)
        else
            null
    val thirstEmpty =
        if (thirstDrain > 0)
            Date(currDate.time + thirstDrain.toLong() + buffer)
        else
            null

    if (healthLow != null)
        setAlarm(context, AlarmItem(1, "Your Cat isn't feeling so good!", healthLow))
    if (healthEmpty != null)
        setAlarm(context, AlarmItem(2, "Your Cat is Dead :(", healthEmpty))
    if (hungerEmpty != null)
        setAlarm(context, AlarmItem(3, "Your Cat is Hungry!", hungerEmpty))
    if (thirstEmpty != null)
        setAlarm(context, AlarmItem(4, "Your Cat is Thirsty!", thirstEmpty))
}

fun setAlarm(context: Context, alarmItem: AlarmItem) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra("alarm_item", alarmItem)
    val pendingIntent = PendingIntent.getBroadcast(context, alarmItem.id, intent, PendingIntent.FLAG_IMMUTABLE)
    val mainActivityIntent = Intent(context, MainActivity::class.java)
    val basicPendingIntent = PendingIntent.getActivity(context, alarmItem.id, mainActivityIntent, PendingIntent.FLAG_IMMUTABLE)
    val clockInfo = AlarmManager.AlarmClockInfo(alarmItem.date.time, basicPendingIntent)
    alarmManager.setAlarmClock(clockInfo, pendingIntent)
    println("${alarmItem.message}, ${alarmItem.date}") //for testing
}

fun cancelAlarm(context: Context, alarmItem: AlarmItem){
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra("alarm_item", alarmItem)
    val pendingIntent = PendingIntent.getBroadcast(context, alarmItem.id, intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.cancel(pendingIntent)
}