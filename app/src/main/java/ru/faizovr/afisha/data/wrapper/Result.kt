package ru.faizovr.afisha.data.wrapper

sealed class Result<out T> {
    class Success<out T>(val value: T) : Result<T>()
    class Error(val exception: Exception) : Result<Nothing>()
}