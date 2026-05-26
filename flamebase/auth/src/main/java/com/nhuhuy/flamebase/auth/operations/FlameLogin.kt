package com.nhuhuy.flamebase.auth.operations

import com.google.firebase.auth.AuthCredential
import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.auth.toFlameUser
import com.nhuhuy.flamebase.core.result.FlameResult
import com.nhuhuy.flamebase.core.utils.FlameCall
import kotlinx.coroutines.tasks.await
import kotlin.time.Duration

/**
 * Signs in using an email and password.
 * 
 * @param email The user's email address.
 * @param password The user's password.
 * @param timeout Optional maximum duration for the operation.
 * @return [FlameResult] containing the [FlameAuth.User] on success.
 */
suspend fun FlameAuth.signIn(
    email: String,
    password: String,
    timeout: Duration? = null,
): FlameResult<FlameAuth.User> = FlameCall.call(timeout = timeout) {
    auth.signInWithEmailAndPassword(email, password).await().user?.toFlameUser()
        ?: error("User not found")
}

/**
 * Signs in with the given [AuthCredential]. 
 * Used for third-party providers like Google, Facebook, etc.
 */
suspend fun FlameAuth.signIn(
    credential: AuthCredential,
    timeout: Duration? = null,
): FlameResult<FlameAuth.User> = FlameCall.call(timeout = timeout) {
    auth.signInWithCredential(credential).await().user?.toFlameUser() ?: error("User not found")
}

/**
 * Signs in as an anonymous user.
 */
suspend fun FlameAuth.signIn(
    timeout: Duration? = null,
): FlameResult<FlameAuth.User> = FlameCall.call(timeout = timeout) {
    auth.signInAnonymously().await().user?.toFlameUser() ?: error("User not found")
}

/**
 * Signs out the currently authenticated user.
 */
fun FlameAuth.signOut() {
    auth.signOut()
}
