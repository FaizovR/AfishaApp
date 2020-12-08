package ru.faizovr.afisha.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Long,
    val slug: String,
    val name: String,
) : Parcelable