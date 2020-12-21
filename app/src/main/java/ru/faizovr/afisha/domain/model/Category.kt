package ru.faizovr.afisha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long,
    val slug: String,
    val name: String,
) : Parcelable