package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.*

@Serializable
data class MoviesListPageDTO (
    val page: Long,

    @SerialName("results")
    val movies: List<MovieDTO>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)
