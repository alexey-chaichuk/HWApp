package ru.chaichuk.hwapp.db.daos

import androidx.room.*
import ru.chaichuk.hwapp.db.MoviesDbContract
import ru.chaichuk.hwapp.db.entities.GenreEntity

@Dao
interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(genre: GenreEntity)

    @Query("DELETE FROM " + MoviesDbContract.Genres.TABLE_NAME)
    suspend fun deleteAll()

    @Delete
    suspend fun delete(genre : GenreEntity)

    @Query("SELECT * FROM " + MoviesDbContract.Genres.TABLE_NAME + " ORDER BY " + MoviesDbContract.Genres.COLUMN_NAME_ID + " ASC")
    suspend fun getAll(): List<GenreEntity>

    @Query("SELECT * FROM "  + MoviesDbContract.Genres.TABLE_NAME + " WHERE " + MoviesDbContract.Genres.COLUMN_NAME_ID +  " = :id")
    suspend fun getById(id: Int): GenreEntity
}