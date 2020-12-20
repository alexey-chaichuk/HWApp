package ru.chaichuk.hwapp.fragments

import ru.chaichuk.hwapp.data.Movie

interface OnMoviesListClickListener {
    fun onMovieClick(movie: Movie)
}