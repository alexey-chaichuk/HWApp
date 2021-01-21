package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDTO (

    @SerialName("id")
    val id: Long? = null,

    @SerialName("cast")
    val cast: List<CastDTO>,

    @SerialName("crew")
    val crew: List<CastDTO>
)

