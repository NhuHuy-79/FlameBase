package com.nhuhuy.flamebase.data.repository

import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.core.result.FlameResult

interface AuthRepository {
    suspend fun signIn(email: String, password: String): FlameResult<FlameAuth.User>
    suspend fun signUp(email: String, password: String, name: String): FlameResult<FlameAuth.User>
    suspend fun resetPassword(email: String): FlameResult<Unit>
    fun signOut()
    val currentUser: FlameAuth.User?
}
