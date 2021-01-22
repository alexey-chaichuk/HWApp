package ru.chaichuk.hwapp.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.chaichuk.hwapp.data.Movie

class MoviesDbRepository (applicationContext : Context) {

    private val moviesDb = MoviesDataBase.create(applicationContext)

    suspend fun getAllMovies(): List<Movie> = withContext(Dispatchers.IO) {
        moviesDb.moviesListDao.getAll()
    }

    suspend fun saveAllMovies(movies : List<Movie>) = withContext(Dispatchers.IO) {
        moviesDb.moviesListDao.deleteAll()
        moviesDb.moviesListDao.saveAll(movies)
    }

}