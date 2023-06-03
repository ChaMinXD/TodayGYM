package com.example.TodayGYM.Sharedprefs

import android.app.AlarmManager
import android.app.Application

internal var alarmManager: AlarmManager? = null

class App:Application() {
    companion object {
        lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPreferences(applicationContext)
        super.onCreate()
    }
}