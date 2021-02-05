package ru.chaichuk.hwapp.viewModels

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.BuildConfig
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.api_v3.MovieDbApi
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.MoviesListLoader
import ru.chaichuk.hwapp.db.MoviesDbRepository
import ru.chaichuk.hwapp.utils.log

class MoviesListViewModel : ViewModel() {

    private val movies: MutableList<Movie> = mutableListOf()
    private val moviesFlow: Flow<List<Movie>>

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData<Boolean>(false)

    //val moviesList: LiveData<List<Movie>> get() = moviesFlow.asLiveData()
    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    init {
        val moviesDbRepository = MoviesDbRepository(HWApp.appContext())
        moviesFlow = moviesDbRepository.getAllMoviesAsFlow()

        viewModelScope.launch {
            moviesFlow.collect { _mutableMoviesList.postValue(it.log()) }
        }

        viewModelScope.launch {
            _mutableLoadingState.value = true

            Log.d("HWApp", "<--- starting data receiving from internet")
            val movieDbApi = MovieDbApi()
            val moviesDTO = movieDbApi.getPopularMovies()

            for (movieDTO in moviesDTO) {
                val movieDetailsWithCreditsDTO =
                    movieDbApi.getMovieDetailsWithCredits((movieDTO.id.toInt()))
                val genres: List<Genre> =
                    movieDetailsWithCreditsDTO.genres.map { Genre(it.id.toInt(), it.name) }
                val actors: List<Actor> = movieDetailsWithCreditsDTO.credits.cast.mapNotNull {
                    it.profilePath?.let { picture ->
                        Actor(
                            it.id.toInt(),
                            it.name,
                            BuildConfig.BASE_IMAGE_W200_URL + picture
                        )
                    }
                }

                val movie = Movie(
                    movieDTO.id.toInt(),
                    movieDTO.title,
                    movieDTO.overview,
                    BuildConfig.BASE_IMAGE_W780_URL + movieDTO.posterPath,
                    BuildConfig.BASE_IMAGE_W780_URL + movieDTO.backdropPath,
                    movieDTO.voteAverage.toFloat(),
                    movieDTO.voteCount.toInt(),
                    if (movieDTO.adult) 16 else 13,
                    movieDetailsWithCreditsDTO.runtime.toInt(),
                    genres,
                    actors,
                    true
                )
                movies.add(movie)
            }

            Log.d("HWApp", "<--- delay in receiving data from internet")
            delay(2_000)

            _mutableLoadingState.value = false
            Log.d("HWApp", "<--- saving data to database")
            moviesDbRepository.saveAllMovies(movies)
            Log.d("HWApp", "<--- data saved to database")
        }
    }
}