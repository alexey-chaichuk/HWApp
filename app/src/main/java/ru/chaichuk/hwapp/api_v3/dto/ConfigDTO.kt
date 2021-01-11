package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigDTO (

    @SerialName("images")
    val imagesConfig: ImagesConfigDTO,

    @SerialName("change_keys")
    val changeKeys: List<String>
)
