package com.nhuhuy.flamebase.data.repository

import com.google.firebase.auth.FirebaseUser
import com.nhuhuy.flamebase.core.result.FlameResult

interface AuthRepository {
    suspend fun signIn(email: String, password: String): FlameResult<FirebaseUser>
    suspend fun signUp(email: String, password: String, name: String): FlameResult<FirebaseUser>
    suspend fun resetPassword(email: String): FlameResult<Unit>
    fun signOut()
    val currentUser: FirebaseUser?
}
