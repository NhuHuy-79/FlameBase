package com.nhuhuy.flamebase.ui.auth

import androidx.lifecycle.viewModelScope
import com.nhuhuy.flamebase.auth.error.toFlameAuthError
import com.nhuhuy.flamebase.core.result.onError
import com.nhuhuy.flamebase.core.result.onSuccess
import com.nhuhuy.flamebase.data.repository.AuthRepository
import com.nhuhuy.flamebase.ui.common.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseMviViewModel<AuthState, AuthIntent, AuthEffect>() {

    init {
        updateState { state -> state.copy(isUserLoggedIn = authRepository.currentUser != null) }
    }

    override fun initialState() = AuthState()

    override fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.EmailChanged -> updateState { state -> state.copy(email = intent.email) }
            is AuthIntent.PasswordChanged -> updateState { state -> state.copy(password = intent.password) }
            is AuthIntent.NameChanged -> updateState { state -> state.copy(name = intent.name) }
            AuthIntent.SignInClicked -> signIn()
            AuthIntent.SignUpClicked -> signUp()
            AuthIntent.ResetPasswordClicked -> resetPassword()
            AuthIntent.SignOutClicked -> signOut()
        }
    }

    private fun signIn() {
        val email = state.value.email
        val password = state.value.password

        if (email.isBlank() || password.isBlank()) {
            sendEffect(AuthEffect.ShowMessage("Email and Password cannot be empty"))
            return
        }

        viewModelScope.launch {
            updateState { state -> state.copy(isLoading = true) }
            authRepository.signIn(email, password)
                .onSuccess {
                    updateState { state -> state.copy(isLoading = false, isUserLoggedIn = true) }
                    sendEffect(AuthEffect.NavigateToHome)
                }
                .onError { error ->
                    updateState { state -> state.copy(isLoading = false) }
                    sendEffect(AuthEffect.ShowError(error.toFlameAuthError()))
                }
        }
    }

    private fun signUp() {
        val email = state.value.email
        val password = state.value.password
        val name = state.value.name

        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            sendEffect(AuthEffect.ShowMessage("All fields are required"))
            return
        }

        viewModelScope.launch {
            updateState { state -> state.copy(isLoading = true) }
            authRepository.signUp(email, password, name)
                .onSuccess {
                    updateState { state -> state.copy(isLoading = false, isUserLoggedIn = true) }
                    sendEffect(AuthEffect.NavigateToHome)
                }
                .onError { error ->
                    updateState { state -> state.copy(isLoading = false) }
                    sendEffect(AuthEffect.ShowError(error.toFlameAuthError()))
                }
        }
    }

    private fun resetPassword() {
        val email = state.value.email
        if (email.isBlank()) {
            sendEffect(AuthEffect.ShowMessage("Please enter your email"))
            return
        }

        viewModelScope.launch {
            updateState { state -> state.copy(isLoading = true) }
            authRepository.resetPassword(email)
                .onSuccess {
                    updateState { state -> state.copy(isLoading = false) }
                    sendEffect(AuthEffect.ShowMessage("Reset link sent to your email"))
                }
                .onError { error ->
                    updateState { state -> state.copy(isLoading = false) }
                    sendEffect(AuthEffect.ShowError(error.toFlameAuthError()))
                }
        }
    }

    private fun signOut() {
        authRepository.signOut()
        updateState { state -> state.copy(isUserLoggedIn = false) }
        sendEffect(AuthEffect.NavigateToLogin)
    }
}
