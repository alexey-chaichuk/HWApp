package ru.chaichuk.hwapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ru.chaichuk.hwapp.db.MoviesDbContract

@Entity
@Parcelize
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Genres.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = MoviesDbContract.Genres.COLUMN_NAME_NAME)
    val name: String
    ) :Parcelable
