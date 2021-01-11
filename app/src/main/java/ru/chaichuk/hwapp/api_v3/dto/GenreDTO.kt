package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDTO (

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String
)
