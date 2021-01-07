package ru.chaichuk.hwapp

import android.app.Application

class HWApp : Application() {

    companion object {
        val apiKey = "134d6c69d05a8a5067dabba6dc5d4b0a"
        val apiToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMzRkNmM2OWQwNWE4YTUwNjdkYWJiYTZkYzVkNGIwYSIsInN1YiI6IjVmZjZhOTkxZmRjNGZhMDA0MWNiODI4OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9Kqn6cXU17E0c68SVpm3ERN0chFQIKqPK_e6H5hvB6k"
        val baseUrl = "https://api.themoviedb.org/3/"
    }
}