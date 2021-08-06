package com.example.ihabit

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log




class AlarmService(context: Context) {
    val context = context
    val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val requestCode = 10
    val ACTION_SET_EXACT = "ACTION_SET_EXTRA"
    val ACTION_SET_REPETITIVE_EXACT = "ACTION_SET_REPETITIVE_EXACT"
    val EXTRA_EXACT_ALARM_TIME = "EXTRA_EXACT_ALARM_TIME"

    fun setExactAlarm(timeInMillis: Long,message:String) {
        setAlarm(timeInMillis, getPendingIntent(
            getIntent().apply {
                action = ACTION_SET_EXACT
                putExtra(EXTRA_EXACT_ALARM_TIME, timeInMillis)
                putExtra("message",message)
            }
        ))


    }

    fun setRepetitiveAlarm(timeInMillis: Long,message:String) {
        setAlarm(


            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = ACTION_SET_REPETITIVE_EXACT
                    putExtra(EXTRA_EXACT_ALARM_TIME, timeInMillis)
                    putExtra("message",message)
                }
            )
        )
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

     fun cancelAll(){
        val updateServiceIntent = Intent(context, AlarmReceiver::class.java)
        val pendingUpdateIntent = PendingIntent.getService(context, requestCode, updateServiceIntent, 0)
         try {
             alarmManager.cancel(pendingUpdateIntent)
         } catch (e: Exception) {
             Log.e("CCC", "AlarmManager update was not canceled. $e")
         }


    }


    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


}