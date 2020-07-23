package com.example.paggingsample.network

sealed class CustomResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : CustomResult<T>()
    data class Loading(val status: Boolean) : CustomResult<Nothing>()
    sealed class Error(val exception: Exception) : CustomResult<Nothing>() {
        class RecoverableError(exception: Exception) : Error(exception)
        class NonRecoverableError(exception: Exception) :
            Error(exception)
    }
}