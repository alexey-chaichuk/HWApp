package ru.chaichuk.hwapp.data

interface MoviesListLoader {
    suspend fun loadMoviesList(): List<Movie>
}
