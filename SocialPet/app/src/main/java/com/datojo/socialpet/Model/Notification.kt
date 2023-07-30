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
    val thirstDrain =
        if (stats.thirst.value > 0)
            (stats.thirst.value * stats.secondsInterval * stats.magnitude).toInt()
        else
            0

    var hungerDrain =
        if (stats.hunger.value > 0)
            (stats.hunger.value * stats.secondsInterval * stats.magnitude).toInt()
        else
            0
    if (hungerDrain > thirstDrain) {
        var tmp = stats.hunger.value
        tmp -= (thirstDrain / stats.secondsInterval) / stats.magnitude

        hungerDrain = (tmp * stats.secondsInterval * stats.magnitude * 2).toInt() + thirstDrain
    }

    var tmp = stats.health.value + (hungerDrain / stats.secondsInterval) / stats.magnitude / 2
    if (tmp > 1) tmp = 1f
    val healthDrainLow =
        if (stats.health.value  > .25)
            ((tmp - .25) * stats.secondsInterval * stats.magnitude).toInt() + hungerDrain
        else
            0
    val healthDrainEmpty =
        if (stats.health.value  > .25)
            (tmp * stats.secondsInterval * stats.magnitude).toInt() + hungerDrain
        else
            0

    val healthLow =
        if (healthDrainLow > 0)
            Date(Date().time + healthDrainLow * 1000)
        else
            null
    val healthEmpty =
        if (healthDrainEmpty > 0)
            Date(Date().time + healthDrainEmpty * 1000)
        else
            null
    val hungerEmpty =
        if (hungerDrain > 0)
            Date(Date().time + hungerDrain * 1000)
        else
            null
    val thirstEmpty =
        if (thirstDrain > 0)
            Date(Date().time + thirstDrain * 1000)
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