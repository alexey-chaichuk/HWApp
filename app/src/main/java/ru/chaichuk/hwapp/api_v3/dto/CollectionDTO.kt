package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDTO (

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null
)
