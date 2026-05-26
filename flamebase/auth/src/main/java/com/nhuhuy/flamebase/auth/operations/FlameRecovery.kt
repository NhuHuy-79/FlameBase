package com.nhuhuy.flamebase.auth.operations

import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.core.result.FlameResult
import com.nhuhuy.flamebase.core.utils.FlameCall
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration

/**
 * Sends a password reset email to the specified address.
 */
suspend fun FlameAuth.sendPasswordResetEmail(
    email: String,
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    auth.sendPasswordResetEmail(email).await()
}

/**
 * Completes the password reset process using the action code.
 * 
 * @param code The action code sent via email.
 * @param newPassword The new password to be set.
 */
suspend fun FlameAuth.confirmPasswordReset(
    code: String,
    newPassword: String,
    timeout: Duration? = null,
): FlameResult<Unit> = FlameCall.call(timeout = timeout) {
    auth.confirmPasswordReset(code, newPassword).await()
}

/**
 * Checks if a password reset code is valid and returns the email address associated.
 */
suspend fun FlameAuth.verifyPasswordResetCode(
    code: String,
    timeout: Duration? = null,
): FlameResult<String> = FlameCall.call(timeout = timeout) {
    auth.verifyPasswordResetCode(code).await()
}
