package com.nhuhuy.flamebase.auth.operations

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.core.result.FlameResult
import com.nhuhuy.flamebase.core.utils.FlameCall
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration

/**
 * Creates a new user account with the specified email and password.
 * 
 * @param email The user's email address.
 * @param password The user's password.
 * @param displayName Optional name to set for the user profile immediately after creation.
 * @param timeout Optional maximum duration for the operation.
 * @return [FlameResult] containing the new [FirebaseUser].
 */
suspend fun FlameAuth.signUp(
    email: String,
    password: String,
    displayName: String? = null,
    timeout: Duration? = null,
): FlameResult<FirebaseUser> = FlameCall.call(timeout = timeout) {
    val result = auth.createUserWithEmailAndPassword(email, password).await()
    val user = result.user ?: error("User not found after creation")

    displayName?.let { name ->
        val updates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        user.updateProfile(updates).await()
    }

    user
}

/**
 * Sends a verification email to the currently signed-in user.
 */
suspend fun FlameAuth.sendEmailVerification(
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    currentUser?.sendEmailVerification()?.await() ?: error("No authenticated user to verify")
}
