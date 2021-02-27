package ru.chaichuk.hwapp.fragments

import android.view.View
import ru.chaichuk.hwapp.data.Movie

interface OnMoviesListClickListener {
    fun onMovieClick(movie: Movie, view : View)
}