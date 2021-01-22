package ru.chaichuk.hwapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.chaichuk.hwapp.data.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    abstract val moviesListDao : MoviesListDao

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