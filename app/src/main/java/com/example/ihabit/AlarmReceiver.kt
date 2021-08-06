package com.example.ihabit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import io.karn.notify.Notify
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.ihabit.viewModel.MainViewModel
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    private val notificationId= 101
    val ACTION_SET_EXACT = "ACTION_SET_EXTRA"
    val ACTION_SET_REPETITIVE_EXACT = "ACTION_SET_REPETITIVE_EXACT"
    val EXTRA_EXACT_ALARM_TIME = "EXTRA_EXACT_ALARM_TIME"
    val viewModel=MainViewModel()


    override fun onReceive(context: Context?, intent: Intent?) {

        val timeInMillis = intent!!.getLongExtra(EXTRA_EXACT_ALARM_TIME, 0L)
        val message=intent.getStringExtra("message")

        when (intent.action) {
            ACTION_SET_EXACT -> {
                val builder = NotificationCompat.Builder(context!!, "iHabit")
                    .setSmallIcon(R.drawable.ic_baseline_timer_24)
                    .setContentTitle("IHabit")
                    .setContentText("該做 $message 了!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                with(NotificationManagerCompat.from(context!!)) {
                    notify(notificationId, builder.build())
                }
            }

            ACTION_SET_REPETITIVE_EXACT -> {
                setRepetitiveAlarm(AlarmService(context!!),message!!)
                val builder = NotificationCompat.Builder(context!!, "iHabit")
                    .setSmallIcon(R.drawable.ic_baseline_timer_24)
                    .setContentTitle("IHabit")
                    .setContentText("該做 $message 了!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                with(NotificationManagerCompat.from(context!!)) {
                    notify(notificationId, builder.build())
                }
            }
        }
    }


    private fun setRepetitiveAlarm(alarmService: AlarmService,message: String) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
        }
        alarmService.setRepetitiveAlarm(cal.timeInMillis,message)
    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()

}


