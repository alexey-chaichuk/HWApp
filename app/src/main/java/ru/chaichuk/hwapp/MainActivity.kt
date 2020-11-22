package ru.chaichuk.hwapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenMovies = findViewById<Button>(R.id.buttonMovies);
        btnOpenMovies.setOnClickListener {
            startActivity(Intent(this, MoviesActivity::class.java))
        };

        val btnOpenMovieDetails = findViewById<Button>(R.id.buttonMovieDetails);
        btnOpenMovieDetails.setOnClickListener {
            startActivity(Intent(this, MovieDetailsActivity::class.java))
        };

    }

}