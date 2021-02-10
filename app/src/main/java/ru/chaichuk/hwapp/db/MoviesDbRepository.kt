package ru.chaichuk.hwapp.db

import android.content.Context
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.db.entities.ActorEntity
import ru.chaichuk.hwapp.db.entities.GenreEntity
import ru.chaichuk.hwapp.db.entities.MovieEntity
import ru.chaichuk.hwapp.notifications.MovieNotification
import ru.chaichuk.hwapp.notifications.MovieNotificationImpl
import ru.chaichuk.hwapp.utils.log

class MoviesDbRepository(context: Context) {

    private val movieNotification : MovieNotification = MovieNotificationImpl(context)
    private val moviesDb = MoviesDataBase.create(context)
    private val moviesFlow : Flow<List<Movie>> = getAllMoviesAsFlow()


    fun getMoviesFlow() : Flow<List<Movie>> {
        return moviesFlow
    }

    private fun getAllMoviesAsFlow(): Flow<List<Movie>> {
        movieNotification.initialize()
        return moviesDb.moviesDao.getAllAsFlow().map { movieEntities ->
            movieEntities.map { movieEntity ->
                Movie(
                    id = movieEntity.id,
                    title = movieEntity.title,
                    overview = movieEntity.overview,
                    poster = movieEntity.poster,
                    backdrop = movieEntity.backdrop,
                    ratings = movieEntity.ratings,
                    numberOfRatings = movieEntity.numberOfRatings,
                    minimumAge = movieEntity.minimumAge,
                    runtime = movieEntity.runtime,
                    genres = movieEntity.genreIds.split(",").filter {
                        it.isNotEmpty()
                    }.map {
                        moviesDb.genresDao.getById(it.toInt())
                    }.map {
                        Genre(
                            id = it.id,
                            name = it.name
                        )
                    },
                    actors = movieEntity.actorIds.split(",").filter {
                        it.isNotEmpty()
                    }.map {
                        moviesDb.actorsDao.getById(it.toInt())
                    }.map {
                        Actor(
                            id = it.id,
                            name = it.name,
                            picture = it.picture
                        )
                    },
                    like = movieEntity.like
                ).log("getAllMoviesAsFlow movie item")
            }.getFirstMovieInListForNotification()
        }.flowOn(Dispatchers.IO)
    }

    private fun List<Movie>.getFirstMovieInListForNotification() : List<Movie> {
        if (this.size > 1) {
            movieNotification.showNotification(this[0])
        }
        return this
    }

    suspend fun saveAllMovies(movies: List<Movie>) = withContext(Dispatchers.IO) {

        moviesDb.withTransaction {
            moviesDb.moviesDao.deleteAll()

            for (movie in movies) {
                moviesDb.genresDao.saveAll(movie.genres.map {
                    GenreEntity(
                        id = it.id,
                        name = it.name
                    )
                })
                moviesDb.actorsDao.saveAll(movie.actors.map {
                    ActorEntity(
                        id = it.id,
                        name = it.name,
                        picture = it.picture
                    )
                })
                moviesDb.moviesDao.save(
                    MovieEntity(
                        id = movie.id,
                        title = movie.title,
                        overview = movie.overview,
                        poster = movie.poster,
                        backdrop = movie.backdrop,
                        ratings = movie.ratings,
                        numberOfRatings = movie.numberOfRatings,
                        minimumAge = movie.minimumAge,
                        runtime = movie.runtime,
                        genreIds = movie.genres.joinToString(
                            separator = ",",
                            transform = { it.id.toString() }),
                        actorIds = movie.actors.joinToString(
                            separator = ",",
                            transform = { it.id.toString() }),
                        like = movie.like
                    )
                )
            }
        }
    }
}
