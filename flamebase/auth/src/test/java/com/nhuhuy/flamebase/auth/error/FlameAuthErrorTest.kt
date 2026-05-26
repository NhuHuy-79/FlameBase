package com.nhuhuy.flamebase.auth.error

import com.google.firebase.auth.FirebaseAuthException
import com.nhuhuy.flamebase.core.result.FlamebaseError
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class FlameAuthErrorTest {

    @Test
    fun `toFlameAuthError maps FirebaseAuthException correctly`() {
        val exception = mockk<FirebaseAuthException>()
        every { exception.errorCode } returns "ERROR_INVALID_EMAIL"
        
        val error = exception.toFlameAuthError()
        assertEquals(FlameAuthError.INVALID_EMAIL, error)
    }

    @Test
    fun `toFlameAuthError returns UNKNOWN for unknown error codes`() {
        val exception = mockk<FirebaseAuthException>()
        every { exception.errorCode } returns "SOME_RANDOM_ERROR"
        
        val error = exception.toFlameAuthError()
        assertEquals(FlameAuthError.UNKNOWN, error)
    }

    @Test
    fun `toFlameAuthError returns UNKNOWN for non-FirebaseAuthException`() {
        val exception = RuntimeException("Not Firebase")
        val error = exception.toFlameAuthError()
        assertEquals(FlameAuthError.UNKNOWN, error)
    }

    @Test
    fun `toFlamebaseError maps to FlameAuthError successfully`() {
        val exception = mockk<FirebaseAuthException>()
        every { exception.errorCode } returns "ERROR_USER_NOT_FOUND"
        
        val error = exception.toFlamebaseError()
        assertEquals(FlameAuthError.USER_NOT_FOUND, error)
    }
}
