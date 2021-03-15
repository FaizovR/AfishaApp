package ru.faizovr.afisha.core.presentation.models

import ru.faizovr.afisha.core.domain.models.Lce

interface RefreshableScreenState<DataType> {
    val lce: Lce<DataType>?

    fun isInErrorState(): Boolean =
        lce?.error != null

    fun isLoadedSuccessfully(): Boolean =
        lce?.isFinishedSuccessfully ?: false

    fun isEmptyListInContent(): Boolean =
        lce?.content?.let {
            it is List<*> && it.isEmpty()
        } ?: false


    fun isInLoadingState(): Boolean =
        lce?.loading == true
}