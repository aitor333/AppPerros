package com.example.appperros.ui.prefs

import android.app.Application

class UserApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val pref:prefs = prefs(applicationContext)
    }
}