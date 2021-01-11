package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO (

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("overview")
    val overview: String,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("genre_ids")
    val genreIDS: List<Long>,

    @SerialName("id")
    val id: Long,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("title")
    val title: String,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("vote_count")
    val voteCount: Long,

    @SerialName("video")
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double
)
