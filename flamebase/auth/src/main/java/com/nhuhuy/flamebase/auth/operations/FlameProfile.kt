package com.nhuhuy.flamebase.auth.operations

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.UserProfileChangeRequest
import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.core.result.FlameResult
import com.nhuhuy.flamebase.core.utils.FlameCall
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration
import androidx.core.net.toUri

/**
 * Updates the profile information of the current user.
 */
suspend fun FlameAuth.updateProfile(
    displayName: String? = null,
    photoUrl: String? = null,
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    val user = currentUser ?: error("No authenticated user found")
    val updates = UserProfileChangeRequest.Builder().apply {
        displayName?.let { setDisplayName(it) }
        photoUrl?.let { photoUri = it.toUri() }
    }.build()
    user.updateProfile(updates).await()
}

/**
 * Updates the email address of the current user.
 * Note: Requires recent authentication.
 */
suspend fun FlameAuth.updateEmail(
    newEmail: String,
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    currentUser?.verifyBeforeUpdateEmail(newEmail)?.await() ?: error("No authenticated user found")
}

/**
 * Updates the password of the current user.
 * Note: Requires recent authentication.
 */
suspend fun FlameAuth.updatePassword(
    newPassword: String,
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    currentUser?.updatePassword(newPassword)?.await() ?: error("No authenticated user found")
}

/**
 * Re-authenticates the current user using the provided [AuthCredential].
 * Required before sensitive operations like [updateEmail] or [deleteUser].
 */
suspend fun FlameAuth.reauthenticate(
    credential: AuthCredential,
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    currentUser?.reauthenticate(credential)?.await() ?: error("No authenticated user found")
}

/**
 * Reloads the user account to get the latest data (e.g., email verification status).
 */
suspend fun FlameAuth.reloadUser(
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    currentUser?.reload()?.await() ?: error("No authenticated user found")
}

/**
 * Deletes the user account from Firebase.
 */
suspend fun FlameAuth.deleteUser(
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    currentUser?.delete()?.await() ?: error("No authenticated user found")
}
