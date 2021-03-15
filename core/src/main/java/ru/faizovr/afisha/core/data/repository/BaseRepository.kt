package ru.faizovr.afisha.core.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.faizovr.afisha.core.domain.models.Lce

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> T
    ): Flow<Lce<T>> {
        return flow {
            val response = apiCall.invoke()
            emit(Lce(response))
        }.catch {
            emit(Lce.error(it))
        }.flowOn(Dispatchers.IO)
    }

    fun <T, K> mapResult(list: List<T>, mapper: (T) -> (K)) =
        list.map { mapper(it) }

    fun <T, K> mapResult(response: T, mapper: (T) -> (K)) =
        mapper(response)
}
