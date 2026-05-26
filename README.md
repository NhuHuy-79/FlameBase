# Flamebase

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-purple.svg)](https://kotlinlang.org/)
[![JitPack](https://jitpack.io/v/YOUR_GITHUB_USERNAME/Flamebase.svg)](https://jitpack.io/#YOUR_GITHUB_USERNAME/Flamebase)

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
    // Replace 'Tag' with the latest release version (e.g., 1.0.0)
    implementation("com.github.YOUR_GITHUB_USERNAME:Flamebase:auth:Tag")
    implementation("com.github.YOUR_GITHUB_USERNAME:Flamebase:core:Tag")

    // Firebase BoM is required as Flamebase is a thin wrapper
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
