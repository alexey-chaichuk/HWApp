package ru.chaichuk.hwapp.viewModels

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.BuildConfig
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.api_v3.MovieDbApi
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.MoviesListLoader
import ru.chaichuk.hwapp.db.MoviesDbRepository

class MoviesListViewModel : ViewModel() {

    private val movies: MutableList<Movie> = mutableListOf()
    private val moviesFlow: Flow<List<Movie>>

    private val _mutableLoadingState = MutableLiveData<Boolean>(false)

    val moviesList: LiveData<List<Movie>> get() = moviesFlow.asLiveData()
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    init {
        val moviesDbRepository = MoviesDbRepository(HWApp.appContext())
        moviesFlow = moviesDbRepository.getAllMoviesAsFlow()

        viewModelScope.launch {
            _mutableLoadingState.value = true
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
            delay(2_000)
            _mutableLoadingState.value = false
            moviesDbRepository.saveAllMovies(movies)
        }
    }
}