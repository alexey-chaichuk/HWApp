package ru.chaichuk.hwapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.chaichuk.hwapp.data.Movie

class MovieDetailsViewModel : ViewModel() {

    private val _mutableMovie = MutableLiveData<Movie>()

    val movie: LiveData<Movie> get() = _mutableMovie

    fun loadMovie (newMovie : Movie) {
        _mutableMovie.setValue(newMovie)
    }
}