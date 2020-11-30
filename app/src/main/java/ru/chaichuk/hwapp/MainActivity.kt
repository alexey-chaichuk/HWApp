package ru.chaichuk.hwapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.chaichuk.hwapp.fragments.MoviesDetailsFragment
import ru.chaichuk.hwapp.fragments.MoviesListFragment
import ru.chaichuk.hwapp.fragments.OnMoviesDetailsClickListener
import ru.chaichuk.hwapp.fragments.OnMoviesListClickListener

class MainActivity :
    AppCompatActivity(),
    OnMoviesListClickListener,
    OnMoviesDetailsClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onMovieClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, MoviesDetailsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDetailsBack() {
        supportFragmentManager.popBackStack()
    }
}