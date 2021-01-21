package ru.chaichuk.hwapp.db

import android.provider.BaseColumns

object MoviesDbContract {

    const val DATABASE_NAME = "movies.db"

    object Movies {
        const val TABLE_NAME = "movies"
        const val COLUMN_NAME_ID = BaseColumns._ID
    }

    object Actors {
        const val TABLE_NAME = "actors"
        const val COLUMN_NAME_ID = BaseColumns._ID
    }

    object Genres {
        const val TABLE_NAME = "genres"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "name"
    }
}

