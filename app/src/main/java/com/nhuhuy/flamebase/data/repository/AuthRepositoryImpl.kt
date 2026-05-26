package com.nhuhuy.flamebase.data.repository

import com.google.firebase.auth.FirebaseUser
import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.auth.operations.currentUser
import com.nhuhuy.flamebase.auth.operations.sendPasswordResetEmail
import com.nhuhuy.flamebase.auth.operations.signIn
import com.nhuhuy.flamebase.auth.operations.signOut
import com.nhuhuy.flamebase.auth.operations.signUp
import com.nhuhuy.flamebase.core.result.FlameResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val flameAuth: FlameAuth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): FlameResult<FirebaseUser> {
        return flameAuth.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String, name: String): FlameResult<FirebaseUser> {
        return flameAuth.signUp(email, password, name)
    }

    override suspend fun resetPassword(email: String): FlameResult<Unit> {
        return flameAuth.sendPasswordResetEmail(email)
    }

    override fun signOut() {
        flameAuth.signOut()
    }

    override val currentUser: FirebaseUser?
        get() = flameAuth.currentUser
}
