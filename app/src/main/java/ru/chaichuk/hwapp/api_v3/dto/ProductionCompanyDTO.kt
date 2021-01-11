package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyDTO (

    @SerialName("id")
    val id: Long,

    @SerialName("logo_path")
    val logoPath: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("origin_country")
    val originCountry: String
)
