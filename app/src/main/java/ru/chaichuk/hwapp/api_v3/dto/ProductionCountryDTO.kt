package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDTO (

    @SerialName("iso_3166_1")
    val iso3166_1: String,

    @SerialName("name")
    val name: String
)
