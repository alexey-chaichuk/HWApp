package ru.chaichuk.hwapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.chaichuk.hwapp.data.Genre

class GenresTypeConverter {

    @TypeConverter
    fun fromGenresList(genres : List<Genre>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun toGenresList(genresJson : String) : List<Genre> {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genresJson, type)
    }
}