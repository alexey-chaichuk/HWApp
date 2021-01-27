package ru.chaichuk.hwapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.chaichuk.hwapp.db.MoviesDbContract

@Parcelize
data class Genre(
    val id: Int,
    val name: String
    ) :Parcelable
