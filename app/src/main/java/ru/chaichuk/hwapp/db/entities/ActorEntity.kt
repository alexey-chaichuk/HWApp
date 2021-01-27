package ru.chaichuk.hwapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.chaichuk.hwapp.db.MoviesDbContract

@Entity(tableName = MoviesDbContract.Actors.TABLE_NAME)
data class ActorEntity(

    @PrimaryKey
    @ColumnInfo(name = MoviesDbContract.Actors.COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = MoviesDbContract.Actors.COLUMN_NAME_NAME)
    val name: String,

    @ColumnInfo(name = MoviesDbContract.Actors.COLUMN_NAME_PICTURE)
    val picture: String
)