package ru.chaichuk.hwapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.chaichuk.hwapp.HWApp
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.utils.log

class MoviesListViewModel : ViewModel() {

    private val moviesFlow: Flow<List<Movie>>

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData(false)

    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    init {
        val moviesDbRepository = HWApp.appMoviesDbRepository()
        moviesFlow = moviesDbRepository.getMoviesFlow()

        viewModelScope.launch {
            moviesFlow.collect { _mutableMoviesList.value = it.log("moviesFlow.collect") }
        }

        viewModelScope.launch {
            var i = 1
            while(true) {
                Log.d("HWAppD", "${Thread.currentThread().name} -> ${i++.toString()} min")
                delay(60_000)
            }
        }
    }
}