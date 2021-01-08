package ru.chaichuk.hwapp

import android.app.Application

class HWApp : Application() {

    companion object {
        val apiKey = "134d6c69d05a8a5067dabba6dc5d4b0a"
        val baseUrl = "https://api.themoviedb.org/3/"
    }
}