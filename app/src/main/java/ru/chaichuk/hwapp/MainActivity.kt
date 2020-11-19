package ru.chaichuk.hwapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openMovieDetails(view: View) {
        startActivity(Intent(this, MovieDetailsActivity::class.java))
    }

    fun openMovies(view: View) {
        startActivity(Intent(this, MoviesActivity::class.java))
    }
}