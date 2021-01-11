package ru.chaichuk.hwapp.api_v3.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDTO (

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("gender")
    val gender: Long,

    @SerialName("id")
    val id: Long,

    @SerialName("known_for_department")
    val knownForDepartment: String,

    @SerialName("name")
    val name: String,

    @SerialName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Long? = null,

    @SerialName("character")
    val character: String? = null,

    @SerialName("credit_id")
    val creditID: String,

    @SerialName("order")
    val order: Long? = null,

    @SerialName("department")
    val department: String? = null,

    @SerialName("job")
    val job: String? = null
)
