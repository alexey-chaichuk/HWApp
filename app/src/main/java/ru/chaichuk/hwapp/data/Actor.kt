package ru.chaichuk.hwapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Actor(
    @PrimaryKey
    val id: Int,

    val name: String,
    val picture: String
    ): Parcelable
