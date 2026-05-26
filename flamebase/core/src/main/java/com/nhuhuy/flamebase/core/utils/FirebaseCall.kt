package com.nhuhuy.flamebase.core.utils

import com.nhuhuy.flamebase.core.result.FlameResult
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration


object FlameCall {

    suspend fun <T> call(
        timeout: Duration? = null,
        onError: (Throwable) -> Unit = {},
        block: suspend () -> T
    ): FlameResult<T> {
        return try {

            val result = if (timeout != null) {
                withTimeout(timeout) {
                    block()
                }
            } else {
                block()
            }

            FlameResult.Success(result)

        } catch (e: CancellationException) {
            throw e

        } catch (e: Throwable) {

            onError(e)

            FlameResult.Error(e)
        }
    }

    suspend fun <T> call(
        timeout: Duration? = null,
        onError: (Throwable) -> Unit = {},
        onFinally: () -> Unit = {},
        block: suspend () -> T
    ): FlameResult<T> {

        return try {

            val result = if (timeout != null) {
                withTimeout(timeout) {
                    block()
                }
            } else {
                block()
            }

            FlameResult.Success(result)

        } catch (e: CancellationException) {
            throw e

        } catch (e: Throwable) {

            onError(e)

            FlameResult.Error(e)

        } finally {
            onFinally()
        }
    }
}