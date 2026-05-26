package com.nhuhuy.flamebase.auth.error

import com.google.firebase.auth.FirebaseAuthException
import com.nhuhuy.flamebase.core.result.FlamebaseError

/**
 * Represents the set of specific errors that can occur during Firebase Authentication operations.
 * 
 * @property errorCode The raw Firebase error code string.
 */
enum class FlameAuthError(val errorCode: String) : FlamebaseError {
    // Authentication Errors
    INVALID_CUSTOM_TOKEN("ERROR_INVALID_CUSTOM_TOKEN"),
    CUSTOM_TOKEN_MISMATCH("ERROR_CUSTOM_TOKEN_MISMATCH"),
    INVALID_CREDENTIAL("ERROR_INVALID_CREDENTIAL"),
    INVALID_EMAIL("ERROR_INVALID_EMAIL"),
    WRONG_PASSWORD("ERROR_WRONG_PASSWORD"),
    USER_DISABLED("ERROR_USER_DISABLED"),
    USER_NOT_FOUND("ERROR_USER_NOT_FOUND"),
    USER_TOKEN_EXPIRED("ERROR_USER_TOKEN_EXPIRED"),
    NETWORK_REQUEST_FAILED("ERROR_NETWORK_REQUEST_FAILED"),
    WEAK_PASSWORD("ERROR_WEAK_PASSWORD"),
    EMAIL_ALREADY_IN_USE("ERROR_EMAIL_ALREADY_IN_USE"),
    ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL"),
    REQUIRES_RECENT_LOGIN("ERROR_REQUIRES_RECENT_LOGIN"),
    PROVIDER_ALREADY_LINKED("ERROR_PROVIDER_ALREADY_LINKED"),
    NO_SUCH_PROVIDER("ERROR_NO_SUCH_PROVIDER"),
    INVALID_USER_TOKEN("ERROR_INVALID_USER_TOKEN"),
    USER_MISMATCH("ERROR_USER_MISMATCH"),
    CREDENTIAL_ALREADY_IN_USE("ERROR_CREDENTIAL_ALREADY_IN_USE"),
    TOO_MANY_REQUESTS("ERROR_TOO_MANY_REQUESTS"),
    OPERATION_NOT_ALLOWED("ERROR_OPERATION_NOT_ALLOWED"),
    
    // Action Code Errors
    EXPIRED_ACTION_CODE("ERROR_EXPIRED_ACTION_CODE"),
    INVALID_ACTION_CODE("ERROR_INVALID_ACTION_CODE"),
    
    // Generic
    UNKNOWN("ERROR_UNKNOWN");

    companion object {
        /**
         * Maps a [FirebaseAuthException] to a [FlameAuthError].
         */
        fun fromException(e: FirebaseAuthException): FlameAuthError {
            return entries.find { it.errorCode == e.errorCode } ?: UNKNOWN
        }
    }
}

/**
 * Extension to convert any [Throwable] into a [FlameAuthError].
 */
fun Throwable.toFlameAuthError(): FlameAuthError {
    return (this as? FirebaseAuthException)?.let {
        FlameAuthError.fromException(it)
    } ?: FlameAuthError.UNKNOWN
}

/**
 * Extension to convert any [Throwable] into a [FlamebaseError].
 */
fun Throwable.toFlamebaseError(): FlamebaseError {
    return (this as? FirebaseAuthException)?.let {
        FlameAuthError.fromException(it)
    } ?: FlamebaseError.Unknown
}
