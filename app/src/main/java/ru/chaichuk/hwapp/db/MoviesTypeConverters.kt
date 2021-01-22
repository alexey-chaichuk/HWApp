package ru.chaichuk.hwapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.chaichuk.hwapp.data.Actor
import ru.chaichuk.hwapp.data.Genre

class MoviesTypeConverters {

    @TypeConverter
    fun fromGenres(genres : List<Genre>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.toJson(genres, type)
    }

    @TypeConverter
    fun toGenres(genresJson : String) : List<Genre> {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(genresJson, type)
    }


    @TypeConverter
    fun fromActors(actors : List<Actor>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<Actor>>() {}.type
        return gson.toJson(actors, type)
    }

    @TypeConverter
    fun toActors(actorsJson : String) : List<Actor> {
        val gson = Gson()
        val type = object : TypeToken<List<Actor>>() {}.type
        return gson.fromJson(actorsJson, type)
    }
}