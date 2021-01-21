package ru.chaichuk.hwapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Int,

    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,

    @ColumnInfo(name = "number_of_ratings")
    val numberOfRatings: Int,

    @ColumnInfo(name = "minimum_age")
    val minimumAge: Int,

    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>,

    val like: Boolean
    ) : Parcelable