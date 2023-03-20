package com.example.jettrivia.data

sealed class Result<out T : Any>() {
    class Success<out T : Any>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
    class Error(val exception: Exception) : Result<Nothing>()
}