package com.nhuhuy.flamebase.core.result

sealed interface FirebaseError {
    data object Unknown : FirebaseError
    data object Network : FirebaseError
}