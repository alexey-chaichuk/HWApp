package ru.chaichuk.hwapp

import android.app.Application
import android.content.Context

class HWApp : Application() {

    companion object {
        private lateinit var appContext: Context

        fun appContext() : Context {
            return appContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
    }
}