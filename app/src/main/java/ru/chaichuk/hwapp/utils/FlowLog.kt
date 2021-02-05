package ru.chaichuk.hwapp.utils

import android.util.Log

fun <T> T.log(): T {
    Log.d("HWApp", "log: ${Thread.currentThread().name} -> ${this.toString()}")
    return this
}