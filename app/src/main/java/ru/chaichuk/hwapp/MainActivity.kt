package ru.chaichuk.hwapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.api_v3.MovieDbApi
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
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let(::handleIntent)
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toIntOrNull()
                id?.apply {
                    Log.d("HWApp", "Handle intent : $id")
                    lifecycleScope.launch() {
                        val movie = MovieDbApi().getMovieByIdFromNet(id)
                        HWApp.appMovieNotification().dismissNotification(id)
                        movie?.apply { onMovieDeepLink(movie)}
                    }
                }
            }
        }
    }

    private fun onMovieDeepLink(movie: Movie) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, MoviesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("movie", movie)
                }
            })
            .addToBackStack(null)
            .commit()
    }

    override fun onMovieClick(movie: Movie, view: View) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, MoviesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("movie", movie)
                }
            })
            .addSharedElement(view, movie.id.toString())
            .addToBackStack(null)
            .commit()
    }

    override fun onDetailsBack() {
        supportFragmentManager.popBackStack()
    }
}
