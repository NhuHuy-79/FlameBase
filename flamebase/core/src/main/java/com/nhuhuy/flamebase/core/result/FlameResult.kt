package com.nhuhuy.flamebase.core.result

/**
 * A sealed interface representing the result of a Flamebase operation.
 */
sealed interface FlameResult<out T> {
    /**
     * Successful result containing the data.
     */
    data class Success<out T>(val data: T) : FlameResult<T>

    /**
     * Error result containing the cause and a mapped Flamebase error if applicable.
     */
    data class Error(val throwable: Throwable) : FlameResult<Nothing> {
        /**
         * The mapped [FlamebaseError] extracted from the throwable.
         */
        val error: FlamebaseError
            get() = (throwable as? FlamebaseError) ?: FlamebaseError.Unknown
    }
}

/**
 * Executes the [block] if the result is [FlameResult.Success].
 */
inline fun <T> FlameResult<T>.onSuccess(
    block: (T) -> Unit
): FlameResult<T> {
    if (this is FlameResult.Success) {
        block(data)
    }
    return this
}

/**
 * Executes the [block] if the result is [FlameResult.Success].
 */
suspend inline fun <T> FlameResult<T>.onSuccessSuspend(
    crossinline block: suspend (T) -> Unit
): FlameResult<T> {
    if (this is FlameResult.Success) {
        block(data)
    }
    return this
}

/**
 * Executes the [block] if the result is [FlameResult.Error].
 */
suspend inline fun <T> FlameResult<T>.onErrorSuspend(
    crossinline block: suspend (throwable: Throwable) -> Unit
): FlameResult<T> {
    if (this is FlameResult.Error) {
        block(throwable)
    }
    return this
}

/**
 * Executes the [block] if the result is [FlameResult.Error].
 */
inline fun <T> FlameResult<T>.onError(
    block: (throwable: Throwable) -> Unit
): FlameResult<T> {
    if (this is FlameResult.Error) {
        block(throwable)
    }
    return this
}

/**
 * Folds the result into a single value based on success or error.
 */
inline fun <T, R> FlameResult<T>.fold(
    onSuccess: (value: T) -> R,
    onError: (throwable: Throwable) -> R
): R {
    return when (this) {
        is FlameResult.Success -> onSuccess(data)
        is FlameResult.Error -> onError(throwable)
    }
}

/**
 * Transforms the data of a successful result.
 */
inline fun <T, R> FlameResult<T>.map(
    crossinline transform: (data: T) -> R
): FlameResult<R> {
    return when (this) {
        is FlameResult.Success -> FlameResult.Success(transform(data))
        is FlameResult.Error -> this
    }
}

/**
 * Returns the data if success, or null otherwise.
 */
fun <T> FlameResult<T>.getDataOrNull(): T? {
    return when (this) {
        is FlameResult.Success -> data
        is FlameResult.Error -> null
    }
}

/**
 * Returns the data if success, or throws the error otherwise.
 */
fun <T> FlameResult<T>.getDataOrThrow(): T {
    return when (this) {
        is FlameResult.Success -> data
        is FlameResult.Error -> throw throwable
    }
}
