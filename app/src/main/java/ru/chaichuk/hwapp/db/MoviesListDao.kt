package ru.chaichuk.hwapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.chaichuk.hwapp.data.Movie

@Dao
interface MoviesListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(movies: List<Movie>)

    @Query("DELETE FROM " + MoviesDbContract.Movies.TABLE_NAME)
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Query("SELECT * FROM " + MoviesDbContract.Movies.TABLE_NAME + " ORDER BY _id ASC")
    suspend fun getAll(): List<Movie>

    @Query("SELECT COUNT(_id) FROM " + MoviesDbContract.Movies.TABLE_NAME)
    fun getLocationsCount(): Int
}