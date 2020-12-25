package ru.faizovr.afisha.data.repository

import android.accounts.NetworkErrorException
import retrofit2.HttpException
import retrofit2.Response
import ru.faizovr.afisha.data.wrapper.Result

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        val response: Response<T>
        try {
            response = apiCall.invoke()
        } catch (t: Throwable) {
            return Result.Error(NetworkErrorException("Error occurred due to the network, error - $t"))
        }
        val responseBody = response.body()
        return when {
            !response.isSuccessful -> Result.Error(HttpException(response))
            responseBody == null -> Result.Error(NetworkErrorException(errorMessage))
            else -> Result.Success(responseBody)
        }
    }
}
