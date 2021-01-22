package ru.chaichuk.hwapp.db

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

    @Query("SELECT * FROM " + MoviesDbContract.Movies.TABLE_NAME + " ORDER BY " + MoviesDbContract.Movies.COLUMN_NAME_RATINGS + " DESC")
    suspend fun getAll(): List<Movie>
}
