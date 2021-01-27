package ru.chaichuk.hwapp.db.daos

import androidx.room.*
import ru.chaichuk.hwapp.db.MoviesDbContract
import ru.chaichuk.hwapp.db.entities.ActorEntity

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(actors: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(actor: ActorEntity)

    @Query("DELETE FROM " + MoviesDbContract.Actors.TABLE_NAME)
    suspend fun deleteAll()

    @Delete
    suspend fun delete(genre : ActorEntity)

    @Query("SELECT * FROM " + MoviesDbContract.Actors.TABLE_NAME + " ORDER BY " + MoviesDbContract.Actors.COLUMN_NAME_NAME + " DESC")
    suspend fun getAll(): List<ActorEntity>

    @Query("SELECT * FROM "  + MoviesDbContract.Actors.TABLE_NAME + " WHERE " + MoviesDbContract.Actors.COLUMN_NAME_ID +  " = :id")
    suspend fun getById(id: Int): ActorEntity
}