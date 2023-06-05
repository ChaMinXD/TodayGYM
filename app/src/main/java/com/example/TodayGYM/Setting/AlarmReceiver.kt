package com.example.TodayGYM.Setting

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.TodayGYM.R
import com.example.TodayGYM.SelectActivity

class AlarmReceiver : BroadcastReceiver() {
    val CHANNEL_ID = "Test"
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("alarm","Receiver RUN")
        if(intent.extras?.get("code") == SettingFragment.REQUEST_CODE) {
            var count = intent.getIntExtra("count", 0)
            createNotificationChannel(context)
            val intent = Intent(context, SelectActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 101, intent, 0)

            val contents = " 오늘의 운동을 시작할 시간이에요 ! "

            // Notification
            var builder01 = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.icon_logo)
                setContentTitle("TodayGYM")  // Set Title
                setContentText(contents)   // Set Content
                priority = NotificationCompat.PRIORITY_DEFAULT  // Set PRIORITY
                setContentIntent(pendingIntent) // Notification Click Event
                setAutoCancel(true) // Remove After Click Notification
            }

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                notify(5, builder01.build())
            }
        }
    }

    private fun createNotificationChannel(context: Context?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TodayGYM_Ch"
            val descriptionText = "TodayGYM Notification"
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}