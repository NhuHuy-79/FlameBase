# Flamebase Agent Rules

## Philosophy

- Kotlin-first
- Coroutine-first
- Thin wrapper over Firebase SDK
- Avoid unnecessary abstraction
- Prefer extension functions
- Keep APIs minimal

## Code Rules

- Never swallow CancellationException
- Use Duration instead of Long for timeout
- Prefer immutable models
- Prefer sealed interfaces for result/error

## Architecture

- flamebase-core must not depend on Firebase services
- auth logic belongs in flamebase-auth
- avoid Android UI dependencies

## API Design

Prefer:
auth.signIn(email, password)

Avoid:
performFirebaseAuthentication()

# Validation Rules

- Never leave compilation errors
- Never leave unresolved references
- Never leave red code in IDE
- Verify imports after changes
- Ensure code builds successfully
- Ensure tests pass before finishing
- Verify Gradle sync compatibility
- Verify coroutine APIs compile
- Verify Flow types match
- Verify visibility modifiers
- Verify extension receiver correctness