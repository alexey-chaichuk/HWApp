package ru.chaichuk.hwapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.chaichuk.hwapp.api_v3.MovieDbApi
import ru.chaichuk.hwapp.data.*
import ru.chaichuk.hwapp.fragments.MoviesDetailsFragment
import ru.chaichuk.hwapp.fragments.OnMoviesDetailsClickListener
import ru.chaichuk.hwapp.fragments.OnMoviesListClickListener

class MainActivity :
    AppCompatActivity(),
    OnMoviesListClickListener,
    OnMoviesDetailsClickListener,
    MoviesListLoader {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    override suspend fun loadMoviesList(): List<Movie> =
        withContext(Dispatchers.IO) {
            //delay(3_000)
            //loadMovies(applicationContext)

            val pictureBasePath = "https://image.tmdb.org/t/p/w780"
            val movieDbApi = MovieDbApi()
            val movies : MutableList<Movie> = mutableListOf()
            val moviesDTO = movieDbApi.getPopularMovies()
            for (movieDTO in moviesDTO) {
                val movieDetailsDTO = movieDbApi.getMovieDetails(movieDTO.id.toInt())
                val movieCreditsDTO = movieDbApi.getMovieCredits(movieDTO.id.toInt())
                val genres : List<Genre> = movieDetailsDTO.genres.map { Genre(it.id.toInt(), it.name) }
                val actors : List<Actor> = movieCreditsDTO.cast.mapNotNull {
                    it.profilePath?.let { picture ->
                        Actor(
                            it.id.toInt(),
                            it.name,
                            pictureBasePath + picture
                        )
                    }
                }

                val movie = Movie (
                    movieDTO.id.toInt(),
                    movieDTO.title,
                    movieDTO.overview,
                    pictureBasePath + movieDTO.posterPath,
                    pictureBasePath + movieDTO.backdropPath,
                    movieDTO.voteAverage.toFloat(),
                    movieDTO.voteCount.toInt(),
                    if(movieDTO.adult) 16 else 13,
                    movieDetailsDTO.runtime.toInt(),
                    genres,
                    actors,
                    true
                )
                movies.add(movie)
            }
            return@withContext movies

        }
}
