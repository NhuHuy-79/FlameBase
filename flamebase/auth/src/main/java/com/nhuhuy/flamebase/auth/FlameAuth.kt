package com.nhuhuy.flamebase.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

object FlameAuth {
    val auth: FirebaseAuth
        get() = Firebase.auth
}
