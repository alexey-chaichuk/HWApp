package ru.chaichuk.hwapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.chaichuk.hwapp.db.MoviesDbContract

@Entity(tableName = MoviesDbContract.Genres.TABLE_NAME,
        indices = [Index(MoviesDbContract.Genres.COLUMN_NAME_ID)])
data class GenreEntity(

    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Genres.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = MoviesDbContract.Genres.COLUMN_NAME_NAME)
    val name: String
)
