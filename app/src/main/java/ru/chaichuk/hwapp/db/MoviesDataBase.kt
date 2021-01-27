package ru.chaichuk.hwapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.chaichuk.hwapp.db.daos.ActorsDao
import ru.chaichuk.hwapp.db.daos.GenresDao
import ru.chaichuk.hwapp.db.daos.MoviesDao
import ru.chaichuk.hwapp.db.entities.ActorEntity
import ru.chaichuk.hwapp.db.entities.GenreEntity
import ru.chaichuk.hwapp.db.entities.MovieEntity

@Database(entities = [MovieEntity::class, ActorEntity::class, GenreEntity::class], version = 2)
abstract class MoviesDataBase : RoomDatabase() {

    abstract val moviesDao : MoviesDao
    abstract val actorsDao : ActorsDao
    abstract val genresDao : GenresDao

    companion object {

        fun create(applicationContext: Context): MoviesDataBase = Room.databaseBuilder(
            applicationContext,
            MoviesDataBase::class.java,
            MoviesDbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}