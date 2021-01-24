package ru.chaichuk.hwapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.chaichuk.hwapp.db.MoviesDbContract

@Entity(tableName = MoviesDbContract.Movies.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_OVERVIEW)
    val overview: String,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_POSTER)
    val poster: String,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_BACKDROP)
    val backdrop: String,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_RATINGS)
    val ratings: Float,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_NUMBER_OF_RATINGS)
    val numberOfRatings: Int,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_MINIMUM_AGE)
    val minimumAge: Int,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_RUNTIME)
    val runtime: Int,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_GENRES)
    val genreIds: String,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_ACTORS)
    val actorIds: String,

    @ColumnInfo(name = MoviesDbContract.Movies.COLUMN_NAME_LIKE)
    val like: Boolean
)