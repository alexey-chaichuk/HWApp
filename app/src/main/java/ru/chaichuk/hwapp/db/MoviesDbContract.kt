package ru.chaichuk.hwapp.db

import android.provider.BaseColumns

object MoviesDbContract {

    const val DATABASE_NAME = "movies.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER = "poster"
        const val COLUMN_NAME_BACKDROP = "backdrop"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_NUMBER_OF_RATINGS = "number_of_ratings"
        const val COLUMN_NAME_MINIMUM_AGE = "minimum_age"
        const val COLUMN_NAME_RUNTIME = "runtime"
        const val COLUMN_NAME_GENRES = "genres"
        const val COLUMN_NAME_ACTORS = "actors"
        const val COLUMN_NAME_LIKE = "like"
    }

}
