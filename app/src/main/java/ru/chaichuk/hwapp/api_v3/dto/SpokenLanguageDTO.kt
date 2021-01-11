package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageDTO (
    @SerialName("iso_639_1")
    val iso639_1: String,

    @SerialName("name")
    val name: String
)
