package com.nhuhuy.flamebase.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

sealed interface AuthenticatedState {
    object Authenticated : AuthenticatedState
    object Unauthenticated : AuthenticatedState
}

/**
 * Main entry point for Flamebase Authentication.
 * 
 * Provides access to the underlying [FirebaseAuth] and the current [FirebaseUser].
 */
object FlameAuth {

    /**
     * The [FirebaseAuth] instance used by the library.
     */
    val auth: FirebaseAuth
        get() = Firebase.auth

    /**
     * The currently signed-in [FirebaseUser], or null if none.
     */
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun getAuthenticatedToken() : String? {
        return getAuthenticatedTokenInformation()?.token
    }

    suspend fun getAuthenticatedExpiryTime() : Long ? {
        return getAuthenticatedTokenInformation()?.expirationTimestamp
    }

    suspend fun getAuthenticatedTokenInformation(): GetTokenResult? {
        return auth.currentUser?.getIdToken(true)?.await()
    }
    val authenticatedFlow: Flow<AuthenticatedState>
        get() {
            return callbackFlow {
                val listener = FirebaseAuth.AuthStateListener { auth ->
                    trySend(if (auth.currentUser == null) AuthenticatedState.Unauthenticated else AuthenticatedState.Authenticated)
                }
                auth.addAuthStateListener(listener)
                awaitClose {
                    auth.removeAuthStateListener(listener)
                }
            }
        }

}
