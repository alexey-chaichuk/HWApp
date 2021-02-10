package ru.chaichuk.hwapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.fragments.MoviesDetailsFragment
import ru.chaichuk.hwapp.fragments.OnMoviesDetailsClickListener
import ru.chaichuk.hwapp.fragments.OnMoviesListClickListener

class MainActivity :
    AppCompatActivity(),
    OnMoviesListClickListener,
    OnMoviesDetailsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            intent?.let(::handleIntent)
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                if (id != null) {
                    Log.d("HWApp", "Handle intent : ${id}")
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, MoviesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("movie", movie)
                }
            })
            .addToBackStack(null)
            .commit()
    }

    override fun onDetailsBack() {
        supportFragmentManager.popBackStack()
    }
}
