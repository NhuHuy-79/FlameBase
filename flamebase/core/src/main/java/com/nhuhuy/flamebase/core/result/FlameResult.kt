package com.nhuhuy.flamebase.core.result

sealed interface FlameResult <out T> {
    data class Success<out T>(val data: T) : FlameResult<T>
    data class Error(val throwable: Throwable) : FlameResult<Nothing>
}


inline fun <T> FlameResult<T>.onSuccess(
    block: (T) -> Unit
) : FlameResult<T>{
    if (this is FlameResult.Success) {
        block(data)
    }
    return this
}

suspend inline fun <T> FlameResult<T>.onSuccessSuspend(
    crossinline block: suspend (T) -> Unit
) : FlameResult<T>{
    if (this is FlameResult.Success) {
        block(data)
    }
    return this
}

suspend inline fun <T> FlameResult<T>.onErrorSuspend(
    crossinline block: suspend (throwable: Throwable) -> Unit
) : FlameResult<T>{
    if (this is FlameResult.Error) {
        block(throwable)
    }
    return this
}


inline fun <T> FlameResult<T>.onError(
    block: (throwable: Throwable) -> Unit
) : FlameResult<T>{
    if (this is FlameResult.Error) {
        block(throwable)
    }
    return this
}

inline fun <T> FlameResult<T>.fold(
    onSuccess: (value: T) -> Unit,
    onError: (throwable: Throwable) -> Unit
) : FlameResult<T>{
    when (this) {
        is FlameResult.Success -> onSuccess(data)
        is FlameResult.Error -> onError(throwable)
    }
    return this
}

inline fun <T, R> FlameResult<T>.map(
    crossinline transform: (data: T) -> R
): FlameResult<R> {
    return when (this) {
        is FlameResult.Success -> FlameResult.Success(transform(data))
        is FlameResult.Error -> this
    }
}

fun <T> FlameResult<T>.getDataOrNull() : T?{
    return when (this) {
        is FlameResult.Success -> data
        is FlameResult.Error -> null
    }
}

fun <T> FlameResult<T>.getDataOrThrow() : T{
    return when (this) {
        is FlameResult.Success -> data
        is FlameResult.Error -> throw throwable
    }
}


