package ru.chaichuk.hwapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.data.MoviesListLoader

class MoviesListViewModel : ViewModel(){

    var moviesListLoader: MoviesListLoader? = null
    private var movies: List<Movie> = emptyList()

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData<Boolean>(false)


    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    fun loadMoviesList() {
        if(movies.isEmpty()) {
            moviesListLoader?.let {
                viewModelScope.launch {
                    _mutableLoadingState.setValue(true)
                    movies = it.loadMoviesList()
                    _mutableMoviesList.setValue(movies)
                    _mutableLoadingState.setValue(false)
                }
            }
        }
    }
}