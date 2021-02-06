package ru.chaichuk.hwapp.utils

import android.util.Log

fun <T> T.log(msg : String = ""): T {
    Log.d("HWApp", "log: ${Thread.currentThread().name} -> ${msg} -> ${this.toString()}")
    return this
}