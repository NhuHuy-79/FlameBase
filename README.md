# Flamebase

<p align="center">
<img src="https://ziadoua.github.io/m3-Markdown-Badges/badges/Kotlin/kotlin3.svg" alt=""> 
<img src="https://ziadoua.github.io/m3-Markdown-Badges/badges/Firebase/firebase3.svg" alt="">
<img src="https://ziadoua.github.io/m3-Markdown-Badges/badges/Android/android2.svg" alt="">
</p>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-purple.svg)](https://kotlinlang.org/)
[![JitPack](https://jitpack.io/v/NhuHuy-79/FlameBase.svg)](https://jitpack.io/NhuHuy-79/Flamebase)

**Flamebase** is a Kotlin-first library that provides a thin and powerful wrapper for the Firebase
SDK on Android. Designed to hide Data layer complexity, it leverages Coroutines/Flow and adheres to
Clean Architecture principles.

## 📦 Installation

### Step 1: Add the JitPack repository to your build file

Add it in your `settings.gradle.kts` at the end of repositories:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add the dependency

Add the library to your app module's `build.gradle.kts`:

```kotlin
dependencies {
    // Flamebase Core & Auth Modules (Current Version: 1.0.0)
    implementation("com.github.NhuHuy-79.FlameBase:core:1.0.0")
    implementation("com.github.NhuHuy-79.FlameBase:auth:1.0.0")

    // Firebase BoM is required as Flamebase operates as a thin wrapper
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
}
```

## 📖 Usage (Auth Module)

### Sign In with Email & Password

```kotlin
viewModelScope.launch {
    FlameAuth.signIn(email, password)
        .onSuccess { user ->
            // 'user' is FlameAuth.User class, not FirebaseUser
            println("Welcome ${user.displayName}")
        }
        .onError { error ->
            // Error handling mapped to FlameAuthError
        }
}
```

### Observe Auth State (Reactive)

```kotlin
FlameAuth.authenticatedFlow
    .collect { state ->
        when (state) {
            is AuthenticatedState.Authenticated -> // Navigate to Home
            is AuthenticatedState.Unauthenticated -> // Navigate to Login
        }
    }
```

## 🛡 Design Philosophy

- **Domain-Driven**: Firebase SDK classes are hidden behind domain models.
- **Coroutines First**: No more callbacks, just `suspend` and `Flow`.
- **Fail-safe**: Centralized error handling via `FlameResult`.

## 📄 License

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for more information.
