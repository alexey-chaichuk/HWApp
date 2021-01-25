package ru.chaichuk.hwapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.BuildConfig
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.api_v3.MovieDbApi
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.MoviesListLoader
import ru.chaichuk.hwapp.db.MoviesDbRepository

class MoviesListViewModel : ViewModel(){

    private val movies : MutableList<Movie> = mutableListOf()

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData<Boolean>(false)


    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    fun loadMoviesList() {
        if(_mutableMoviesList.value?.isEmpty() == true) {

            viewModelScope.launch {

                _mutableLoadingState.value = true


                val moviesDbRepository = MoviesDbRepository(HWApp.appContext())
                val moviesFromDb = moviesDbRepository.getAllMovies()
                Log.d("HWApp", moviesFromDb.toString())
                Log.d("HWApp", moviesFromDb.size.toString())
                _mutableMoviesList.value = moviesFromDb

                val movieDbApi = MovieDbApi()
                val moviesDTO = movieDbApi.getPopularMovies()

                for (movieDTO in moviesDTO) {
                    val movieDetailsWithCreditsDTO = movieDbApi.getMovieDetailsWithCredits((movieDTO.id.toInt()))
                    val genres : List<Genre> = movieDetailsWithCreditsDTO.genres.map { Genre(it.id.toInt(), it.name) }
                    val actors : List<Actor> = movieDetailsWithCreditsDTO.credits.cast.mapNotNull {
                        it.profilePath?.let { picture ->
                            Actor(
                                it.id.toInt(),
                                it.name,
                                BuildConfig.BASE_IMAGE_W200_URL + picture
                            )
                        }
                    }

                    val movie = Movie (
                        movieDTO.id.toInt(),
                        movieDTO.title,
                        movieDTO.overview,
                        BuildConfig.BASE_IMAGE_W780_URL + movieDTO.posterPath,
                        BuildConfig.BASE_IMAGE_W780_URL + movieDTO.backdropPath,
                        movieDTO.voteAverage.toFloat(),
                        movieDTO.voteCount.toInt(),
                        if(movieDTO.adult) 16 else 13,
                        movieDetailsWithCreditsDTO.runtime.toInt(),
                        genres,
                        actors,
                        true
                    )
                    movies.add(movie)
                }

                moviesDbRepository.saveAllMovies(movies)

                _mutableMoviesList.value = movies.sortedBy { it.ratings }.asReversed()
                _mutableLoadingState.value = false

            }
        }
    }
}