package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.*

@Serializable
data class MovieDetailsDTO (
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("belongs_to_collection")
    val belongsToCollection: CollectionDTO? = null,

    val budget: Long,
    val genres: List<GenreDTO>,
    val homepage: String,
    val id: Long,

    @SerialName("imdb_id")
    val imdbID: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDTO>,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDTO>,

    @SerialName("release_date")
    val releaseDate: String,

    val revenue: Long,
    val runtime: Long,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDTO>,

    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Long
)

@Serializable
data class CollectionDTO (
    val id: Long,
    val name: String,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("backdrop_path")
    val backdropPath: String
)

@Serializable
data class GenreDTO (
    val id: Long,
    val name: String
)

@Serializable
data class ProductionCompanyDTO (
    val id: Long,

    @SerialName("logo_path")
    val logoPath: String? = null,

    val name: String,

    @SerialName("origin_country")
    val originCountry: String
)

@Serializable
data class ProductionCountryDTO (
    @SerialName("iso_3166_1")
    val iso3166_1: String,

    val name: String
)

@Serializable
data class SpokenLanguageDTO (
    @SerialName("iso_639_1")
    val iso639_1: String,

    val name: String
)