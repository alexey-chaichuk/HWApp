package ru.chaichuk.hwapp.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.chaichuk.hwapp.db.MoviesDbContract
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
}
