package com.nhuhuy.flamebase.core.utils

import com.nhuhuy.flamebase.core.result.FlameResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class FlameCallTest {

    @Test
    fun `call returns success when block succeeds`() = runTest {
        val result = FlameCall.call { "Success" }
        
        assertTrue(result is FlameResult.Success)
        assertEquals("Success", (result as FlameResult.Success).data)
    }

    @Test
    fun `call returns error when block throws`() = runTest {
        val exception = RuntimeException("Failure")
        val result = FlameCall.call { throw exception }
        
        assertTrue(result is FlameResult.Error)
        assertEquals(exception, (result as FlameResult.Error).throwable)
    }

    @Test(expected = kotlinx.coroutines.TimeoutCancellationException::class)
    fun `call returns error on timeout`() = runTest {
        FlameCall.call(timeout = 50.milliseconds) {
            delay(100.milliseconds)
            "Too late"
        }
    }

    @Test
    fun `call propagates CancellationException`() = runTest {
        try {
            FlameCall.call {
                throw kotlinx.coroutines.CancellationException("Cancelled")
            }
            fail("CancellationException should be re-thrown")
        } catch (e: kotlinx.coroutines.CancellationException) {
            assertEquals("Cancelled", e.message)
        }
    }
}
