package com.nhuhuy.flamebase.auth.operations

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.core.result.FlameResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthOperationsTest {

    private lateinit var mockAuth: FirebaseAuth
    private lateinit var mockUser: FirebaseUser

    @Before
    fun setup() {
        mockAuth = mockk(relaxed = true)
        mockUser = mockk(relaxed = true)
        
        // Point FlameAuth to our mock
        FlameAuth.authProvider = { mockAuth }
    }

    @After
    fun tearDown() {
        // Reset to default
        FlameAuth.authProvider = { FirebaseAuth.getInstance() }
    }

    @Test
    fun `signIn returns success when firebase succeeds`() = runTest {
        val mockAuthResult = mockk<AuthResult>()
        every { mockUser.uid } returns "123"
        every { mockUser.email } returns "test@example.com"
        every { mockUser.displayName } returns "Test User"
        every { mockUser.photoUrl } returns null
        every { mockUser.isEmailVerified } returns true
        every { mockAuthResult.user } returns mockUser
        every { mockAuth.signInWithEmailAndPassword(any(), any()) } returns Tasks.forResult(mockAuthResult)

        val result = FlameAuth.signIn("test@example.com", "password")

        assertTrue(result is FlameResult.Success)
        val user = (result as FlameResult.Success).data
        assertEquals("123", user.uid)
        assertEquals("test@example.com", user.email)
        assertEquals("Test User", user.displayName)
        assertEquals(true, user.isEmailVerified)
    }

    @Test
    fun `signIn returns error when firebase fails`() = runTest {
        val exception = mockk<FirebaseAuthException>()
        every { exception.errorCode } returns "ERROR_WRONG_PASSWORD"
        every { mockAuth.signInWithEmailAndPassword(any(), any()) } returns Tasks.forException(exception)

        val result = FlameAuth.signIn("test@example.com", "wrong")

        assertTrue(result is FlameResult.Error)
        val error = (result as FlameResult.Error).throwable
        assertTrue(error is FirebaseAuthException)
        assertEquals("ERROR_WRONG_PASSWORD", (error as FirebaseAuthException).errorCode)
    }

    @Test
    fun `signUp returns success and updates profile`() = runTest {
        val mockAuthResult = mockk<AuthResult>()
        every { mockUser.uid } returns "123"
        every { mockUser.email } returns "new@example.com"
        every { mockUser.displayName } returns "John Doe"
        every { mockUser.photoUrl } returns null
        every { mockUser.isEmailVerified } returns false
        every { mockAuthResult.user } returns mockUser
        every { mockAuth.createUserWithEmailAndPassword(any(), any()) } returns Tasks.forResult(mockAuthResult)
        every { mockUser.updateProfile(any()) } returns Tasks.forResult(null)

        val result = FlameAuth.signUp("new@example.com", "password", "John Doe")

        if (result is FlameResult.Error) {
            println("Error: ${result.throwable}")
        }
        
        assertTrue("Expected Success, but got $result", result is FlameResult.Success)
        val user = (result as FlameResult.Success).data
        assertEquals("123", user.uid)
        assertEquals("John Doe", user.displayName)
    }

    @Test
    fun `sendPasswordResetEmail calls firebase correctly`() = runTest {
        every { mockAuth.sendPasswordResetEmail(any()) } returns Tasks.forResult(null)

        val result = FlameAuth.sendPasswordResetEmail("reset@example.com")

        assertTrue(result is FlameResult.Success)
    }

    @Test
    fun `signOut calls firebase signOut`() {
        FlameAuth.signOut()
        io.mockk.verify { mockAuth.signOut() }
    }
}
