package com.nhuhuy.flamebase.ui.auth

import com.nhuhuy.flamebase.auth.error.FlameAuthError

data class AuthState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isUserLoggedIn: Boolean = false
)

sealed interface AuthIntent {
    data class EmailChanged(val email: String) : AuthIntent
    data class PasswordChanged(val password: String) : AuthIntent
    data class NameChanged(val name: String) : AuthIntent
    object SignInClicked : AuthIntent
    object SignUpClicked : AuthIntent
    object ResetPasswordClicked : AuthIntent
    object SignOutClicked : AuthIntent
}

sealed interface AuthEffect {
    data class ShowError(val error: FlameAuthError) : AuthEffect
    data class ShowMessage(val message: String) : AuthEffect
    object NavigateToHome : AuthEffect
    object NavigateToLogin : AuthEffect
}
