package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
data class MovieDetailsDTO (

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("belongs_to_collection")
    val belongsToCollection: CollectionDTO? = null,

    @SerialName("budget")
    val budget: Long,

    @SerialName("genres")
    val genres: List<GenreDTO>,

    @SerialName("homepage")
    val homepage: String,

    @SerialName("id")
    val id: Long,

    @SerialName("imdb_id")
    val imdbID: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDTO>,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDTO>,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("revenue")
    val revenue: Long,

    @SerialName("runtime")
    val runtime: Long,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDTO>,

    @SerialName("status")
    val status: String,

    @SerialName("tagline")
    val tagline: String,

    @SerialName("title")
    val title: String,

    @SerialName("video")
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Long
)
