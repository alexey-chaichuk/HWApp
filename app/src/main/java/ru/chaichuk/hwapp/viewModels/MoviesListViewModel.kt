package ru.chaichuk.hwapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.api_v3.MovieDbApi
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.MoviesListLoader

class MoviesListViewModel : ViewModel(){

    private val movies : MutableList<Movie> = mutableListOf()

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData<Boolean>(false)


    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    fun loadMoviesList() {
        if(movies.isEmpty()) {

            viewModelScope.launch {

                _mutableLoadingState.setValue(true)

                val pictureBasePath = "https://image.tmdb.org/t/p/w780"
                val movieDbApi = MovieDbApi()
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

                    _mutableMoviesList.setValue(movies)
                    _mutableLoadingState.setValue(false)
                }
            }
        }
    }
}