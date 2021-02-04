package ru.chaichuk.hwapp.db.daos

import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.chaichuk.hwapp.data.Movie
import ru.chaichuk.hwapp.db.MoviesDbContract
import ru.chaichuk.hwapp.db.entities.ActorEntity
import ru.chaichuk.hwapp.db.entities.GenreEntity
import ru.chaichuk.hwapp.db.entities.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: MovieEntity)

    @Query("DELETE FROM " + MoviesDbContract.Movies.TABLE_NAME)
    suspend fun deleteAll()

    @Query("SELECT * FROM " + MoviesDbContract.Movies.TABLE_NAME + " ORDER BY " + MoviesDbContract.Movies.COLUMN_NAME_RATINGS + " DESC")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM " + MoviesDbContract.Movies.TABLE_NAME + " ORDER BY " + MoviesDbContract.Movies.COLUMN_NAME_RATINGS + " DESC")
    fun getAllAsFlow(): Flow<List<MovieEntity>>

}
