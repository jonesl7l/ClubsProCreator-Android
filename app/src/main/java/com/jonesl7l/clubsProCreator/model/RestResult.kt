package com.jonesl7l.clubsProCreator.model

sealed class RestResult<out T : Any?> {

    object Idle : RestResult<Nothing>()
    data class Success<out T : Any?>(val data: T) : RestResult<T>()
    data class ApiError(val message: String, val exception: Exception? = null, val state: Any? = null) : RestResult<Nothing>()
    data class InputError(val state: LoginState) : RestResult<Nothing>()
    object Loading : RestResult<Nothing>()
}