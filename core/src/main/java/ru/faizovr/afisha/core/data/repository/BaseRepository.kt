package ru.faizovr.afisha.core.data.repository

import android.util.Log
import ru.faizovr.afisha.core.domain.models.Lce

open class BaseRepository {

    private val logTag = this::class.java.simpleName

    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> T
    ): Lce<T> {
        return try {
            val response: T = apiCall.invoke()
            Lce.data(response)
        } catch (t: Throwable) {
            Log.e(logTag, "safeApiCall: ${t.stackTrace}")
            Lce.error(t)
        }
    }

    fun <T, K> mapResult(list: List<T>, mapper: (T) -> (K)) =
        list.map { mapper(it) }

    fun <T, K> mapResult(response: T, mapper: (T) -> (K)) =
        mapper(response)
}
